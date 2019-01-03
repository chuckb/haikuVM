package haikuvm.pc.tools;
import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.TargetLostException;
import org.apache.bcel.generic.Visitor;


public class Preprocess {

	private JavaClass jc;
	ClassGen theClassGen;
	ConstantPoolGen theCPool;

	public Preprocess(JavaClass jc) {
		this.jc=jc;
	}

	public JavaClass preprocess() {
		for(Method method : jc.getMethods()) {
			if((method.getName()+method.getSignature()).equals("haikuReleaseLock(Ljava/lang/Object;)V")) {
				haikuReleaseLock(method);
            } else if ((method.getName()+method.getSignature()).contains("clinitHaikuMagic")) {
				;
			} else if(method.isSynchronized()) {
	            synchronizedMethod(method);
			}
		}
		if (theClassGen!=null) {
			jc=theClassGen.getJavaClass();
		}
		return jc;
	}

	private void haikuReleaseLock(Method method) {
		if (theClassGen==null) {
			theClassGen = new ClassGen(jc);
			theCPool = theClassGen.getConstantPool();
		}

		Verbose.println("  PREPROCESSING haikuReleaseLock: "+jc.getClassName()+"."+method.getName()+method.getSignature());
		MethodGen genMethod = new MethodGen(method, theClassGen.getClassName(), theCPool);
		InstructionList ilist = genMethod.getInstructionList();
		if (ilist==null) return; // is probably native
		Instruction r=null;
		for(Instruction i : ilist.getInstructions()) {
			if (isReturn(i)) {
				r=i;
				try {
					ilist.delete(i);
				} catch (TargetLostException e) {
					//e.printStackTrace();
				}
			}
		}
		ilist.insert(InstructionFactory.MONITOREXIT);
		ilist.insert(InstructionFactory.ALOAD_0);
		
		ilist.append(InstructionFactory.ALOAD_0);
		ilist.append(InstructionFactory.MONITORENTER);
		if (r!=null) ilist.append(r);
		
		ilist.setPositions();
		genMethod.setMaxStack();
		genMethod.setMaxLocals();
		genMethod.removeLineNumbers();


		genMethod.setInstructionList(ilist);
		theClassGen.replaceMethod(method, genMethod.getMethod());
	}

	/**
	 * Idea:
	 * transform from:
	 *   code1
	 *   xreturn
	 *   code2
	 * to:
	 *   push this/class
	 *   monitorenter
     *   code1
     *   goto exit
     *   code2
     *  exit:
     *   push this/class
     *   monitorexit
     *   xreturn
     *   
	 * @param method
	 */
	private void synchronizedMethod(Method method) {
		if (theClassGen==null) {
			theClassGen = new ClassGen(jc);
			theCPool = theClassGen.getConstantPool();
		}

		Verbose.println("  PREPROCESSING synchronized: "+jc.getClassName()+"."+method.getName()+method.getSignature());
		
		
		MethodGen genMethod = new MethodGen(method, theClassGen.getClassName(), theCPool);
		InstructionList ilist = genMethod.getInstructionList();
		Instruction r=null;
        InstructionHandle new_target=null;
		if (method.isStatic()) {
			ilist.insert(InstructionFactory.MONITORENTER);
			ilist.insert(new LDC(jc.getClassNameIndex()));
			
			ilist.append(new LDC(jc.getClassNameIndex()));
			new_target = ilist.getEnd();
			ilist.append(InstructionFactory.MONITOREXIT);
		} else {
			ilist.insert(InstructionFactory.MONITORENTER);
			ilist.insert(InstructionFactory.ALOAD_0);
			
			ilist.append(InstructionFactory.ALOAD_0);
			new_target = ilist.getEnd();
			ilist.append(InstructionFactory.MONITOREXIT);
		}
        for (InstructionHandle ih : ilist.getInstructionHandles()) {
            Instruction i=ih.getInstruction();
            if (isReturn(i)) {
                r = i;
                if (!ih.getNext().equals(new_target)) {
                    ilist.insert(ih, new GOTO(new_target));
                }
                try {
                    ilist.delete(ih);
                } catch (TargetLostException e) {
                    // from http://commons.apache.org/bcel/apidocs/org/apache/bcel/generic/TargetLostException.html
                    InstructionHandle[] targets = e.getTargets();
                    for (int tg = 0; tg < targets.length; tg++) {
                        InstructionTargeter[] targeters = targets[tg].getTargeters();
    
                        for (int j = 0; j < targeters.length; j++)
                            targeters[j].updateTarget(targets[tg], new_target);
                    }
                }
            }
        }
		
		if (r!=null) ilist.append(r);
		
		ilist.setPositions();
		genMethod.setMaxStack();
		genMethod.setMaxLocals();
		genMethod.removeLineNumbers();


		genMethod.setInstructionList(ilist);
		theClassGen.replaceMethod(method, genMethod.getMethod());
	}

    private boolean isReturn(Instruction i) {
        return i.equals(InstructionFactory.ARETURN) ||
               i.equals(InstructionFactory.DRETURN) ||
               i.equals(InstructionFactory.FRETURN) ||
               i.equals(InstructionFactory.IRETURN) ||
               i.equals(InstructionFactory.LRETURN) ||
               i.equals(InstructionFactory.RETURN)
        ;
    }

}
