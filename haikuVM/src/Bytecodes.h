/**
 * x=ALOAD_1
 *
 * OP_ALOAD_1  used in haikuJava.c as bytecode index. Generated into haikuConfig.h.
 * OPF_ALOAD_1
 *
 * OPL_ALOAD_1 LABEL(x),BEGCODE(x) for use in bytecodeLabels (bytecodeDispatcher.h)
 *  bytecodeAsJump.h
 *    uses OP_ALOAD_1 as an index into bytecodeLabels to map to jump target OPL_ALOAD_0
 *  threadedAsJump.h
 *    uses OP_ALOAD_1 as direct address to jump target OPL_ALOAD_1 and OP_ALOAD_1 === OPL_ALOAD_1 (bytecodeLabels is NOT used at all)
 *
 * HBC_ALOAD_1  LABEL(x),BEGCODE(x) for use in bytecodeLabels (bytecodeDispatcher.h). Generated into haikuConfig.h.
 *  bytecodeAsSwitch.h
 *    uses OP_ALOAD_1 as an index into bytecodeLabels to map to switch value OPL_ALOAD_0
 *
 *
 *
 */
#ifndef BEGCODE
#define BEGCODE(bc) void OPF_##bc() {
#define ENDCODE };
#define CONSTRAIN0(x)
#define PUSHTOP0()	pushTop0()
#define LOOP_THREAD()
#endif


/**
 * aaload (32) <br>
 *	arrayref, index ==> value<br>
 *	loads onto the stack a reference from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_AALOAD)
BEGCODE(AALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	setMarkBit(top.s1.a);
	if (checkIndex(adr, index)) top.s1.a=((jobjectArray)adr)->array[index];
ENDCODE
#endif

/**
 * aastore (53) <br>
 *	arrayref, index, value ==><br>
 *	stores into a reference in an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_AASTORE)
BEGCODE(AASTORE)
	jobject value=top.s1.a;
	jint index=ipop();
	jobject adr=apop();
	setMarkBit(top.s1.a);
	popTop();
	if (checkIndex(adr, index))	((jobjectArray)adr)->array[index]=value;
ENDCODE
#endif

/**
 * aconst_null (01) <br>
 *	==> null<br>
 *	pushes a null reference onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ACONST_NULL)
BEGCODE(ACONST_NULL)
	pushTop();
	top.s1.a=NULL;
ENDCODE
#endif

/**
 * aload (19) 1: index<br>
 *	==> objectref<br>
 *	loads a reference onto the stack from a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ALOAD)
BEGCODE(ALOAD)	
	pushTop();
	top.s1=getLocal(GETCODEBYTE2())->s0;
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * aload_0 (2a) <br>
 *	==> objectref<br>
 *	loads a reference onto the stack from local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ALOAD_0)
BEGCODE(ALOAD_0)
	CONSTRAIN0(0);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * aload_1 (2b) <br>
 *	==> objectref<br>
 *	loads a reference onto the stack from local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ALOAD_1)
BEGCODE(ALOAD_1)
	CONSTRAIN0(1);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * aload_2 (2c) <br>
 *	==> objectref<br>
 *	loads a reference onto the stack from local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ALOAD_2)
BEGCODE(ALOAD_2)
	CONSTRAIN0(2);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * aload_3 (2d) <br>
 *	==> objectref<br>
 *	loads a reference onto the stack from local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ALOAD_3)
BEGCODE(ALOAD_3)
	CONSTRAIN0(3);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * anewarray (bd) 2: indexbyte1, indexbyte2<br>
 * HAIKU: anewarray (bd) 2: sizeindex<br>
 *	count ==> arrayref<br>
 *	creates a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ANEWARRAY)
BEGCODE(ANEWARRAY)
	void * mem = newarray(top.s1.i, GETCODEADR2());
	PanicAware(mem, top.s1.a=mem);
ENDCODE
#endif

/**
 * areturn (b0) <br>
 *	objectref ==> [empty]<br>
 *	returns a reference from a method<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ARETURN)
BEGCODE(ARETURN)
	areturn();
ENDCODE
#endif

/**
 * arraylength (be) <br>
 *	arrayref ==> length<br>
 *	gets the length of an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ARRAYLENGTH)
BEGCODE(ARRAYLENGTH)
	top.s1.i=top.s1.ay->length;
ENDCODE
#endif

/**
 * astore (3a) 1: index<br>
 *	objectref ==><br>
 *	stores a reference into a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ASTORE)
BEGCODE(ASTORE)
	setLocal(GETCODEBYTE2(), &top.s1);
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

/**
 * astore_0 (4b) <br>
 *	objectref ==><br>
 *	stores a reference into local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ASTORE_0)
BEGCODE(ASTORE_0)
	CONSTRAIN0(0);
	setLocal(bc&7, &top.s1);
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

/**
 * astore_1 (4c) <br>
 *	objectref ==><br>
 *	stores a reference into local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ASTORE_1)
BEGCODE(ASTORE_1)
	CONSTRAIN0(1);
	setLocal(bc&7, &top.s1);
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

/**
 * astore_2 (4d) <br>
 *	objectref ==><br>
 *	stores a reference into local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ASTORE_2)
BEGCODE(ASTORE_2)
	CONSTRAIN0(2);
	setLocal(bc&7, &top.s1);
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

/**
 * astore_3 (4e) <br>
 *	objectref ==><br>
 *	stores a reference into local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ASTORE_3)
BEGCODE(ASTORE_3)
	CONSTRAIN0(3);
	setLocal(bc&7, &top.s1);
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

/**
 * athrow (bf) <br>
 *	objectref ==> [empty], objectref<br>
 *	throws an error or exception (notice that the rest of the stack is cleared, leaving only a reference to the Throwable)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ATHROW)
BEGCODE(ATHROW)
	athrow(top.s1.a);
ENDCODE
#endif

/**
 * baload (33) <br>
 *	arrayref, index ==> value<br>
 *	loads a byte or Boolean value from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_BALOAD)
BEGCODE(BALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index))	top.s1.i=((jbyteArray)adr)->array[index];
ENDCODE
#endif

/**
 * bastore (54) <br>
 *	arrayref, index, value ==><br>
 *	stores a byte or Boolean value into an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_BASTORE)
BEGCODE(BASTORE)
	jint value=top.s1.i;
	jint index=ipop();
	jobject adr=apop();
	popTop();
	if (checkIndex(adr, index))	((jbyteArray)adr)->array[index]=value;
ENDCODE
#endif

/**
 * bipush (10) 1: byte<br>
 *	==> value<br>
 *	pushes a byte onto the stack as an integer value<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_BIPUSH)
BEGCODE(BIPUSH)
	pushTop();
	top.s1.i=(int8_t)GETCODEBYTE2();
ENDCODE
#endif

/**
 * caload (34) <br>
 *	arrayref, index ==> value<br>
 *	loads a char from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_CALOAD)
BEGCODE(CALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index))	top.s1.i=(jchar)(((jchar8or16Array)adr)->array[index]);
ENDCODE
#endif

/**
 * castore (55) <br>
 *	arrayref, index, value ==><br>
 *	stores a char into an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_CASTORE)
BEGCODE(CASTORE)
	jchar value=(jchar)top.s1.i;
	jint index=ipop();
	jobject adr=apop();
	popTop();
	if (checkIndex(adr, index))	((jchar8or16Array)adr)->array[index]=value;
ENDCODE
#endif

/**
 * checkcast (c0) 2: indexbyte1, indexbyte2<br>
 *	objectref ==> objectref<br>
 *	checks whether an objectref is of a certain type, the class reference of which is in the constant pool at index (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_CHECKCAST)
BEGCODE(CHECKCAST)	
	jclass clazz = (jclass)GETCODEADR2();
#if HAIKU_PanicSupport && (HAIKU_PanicExceptions & ClassCastException)
	if (top.s1.a != NULL && instanceOf(top.s1.a, clazz)==0) {
		panic(ClassCastException, (int)getClass(top.s1.a));
	}
#endif
ENDCODE
#endif

/**
 * d2f (90) <br>
 *	value ==> result<br>
 *	converts a double to a float<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_D2F)
BEGCODE(D2F)
	popTop0();
	top.s1.f=FLOAT2TF(TD2DOUBLE(top.d));
ENDCODE
#endif

/**
 * d2i (8e) <br>
 *	value ==> result<br>
 *	converts a double to an int<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_D2I)
BEGCODE(D2I)
	popTop0();
	top.s1.i=TD2DOUBLE(top.d);
ENDCODE
#endif

/**
 * d2l (8f) <br>
 *	value ==> result<br>
 *	converts a double to a long<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_D2L)
BEGCODE(D2L)
	popTop0();
	top.j=TD2DOUBLE(top.d);
	PUSHTOP0();
ENDCODE
#endif

/**
 * dadd (63) <br>
 *	value1, value2 ==> result<br>
 *	adds two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DADD)
BEGCODE(DADD)
	popTop0();	
	top.d=DOUBLE2TD(TD2DOUBLE(top.d)+TD2DOUBLE(popp2()->d));
	PUSHTOP0();
ENDCODE
#endif

/**
 * daload (31) <br>
 *	arrayref, index ==> value<br>
 *	loads a double from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DALOAD)
BEGCODE(DALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index))	top.d=((jdoubleArray)adr)->array[index];
	PUSHTOP0();
ENDCODE
#endif

/**
 * dastore (52) <br>
 *	arrayref, index, value ==><br>
 *	stores a double into an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DASTORE)
BEGCODE(DASTORE)
	jint index;
	jobject adr;
	jdouble d;
	popTop0();
	d=top.d;
	index=ipop();
	adr=apop();
	popTop();
	if (checkIndex(adr, index))	((jdoubleArray)adr)->array[index]=d;
ENDCODE
#endif

/**
 * dcmpg (98) <br>
 *	value1, value2 ==> result<br>
 *	compares two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DCMPG)
BEGCODE(DCMPG)
	double v2, v1;
	popTop0();
	v2=TD2DOUBLE(top.d);
	v1=TD2DOUBLE(popp2()->d);
	if (v2<v1) top.s1.i=1; 
	else if (v2>v1) top.s1.i=-1; 
	else if (v1!=v1 || v2!=v2) top.s1.i=1; 
	else top.s1.i=0; 
ENDCODE
#endif

/**
 * dcmpl (97) <br>
 *	value1, value2 ==> result<br>
 *	compares two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DCMPL)
BEGCODE(DCMPL)
	double v2, v1;
	popTop0();
	v2=TD2DOUBLE(top.d);
	v1=TD2DOUBLE(popp2()->d);
	if (v2<v1) top.s1.i=1; 
	else if (v2>v1) top.s1.i=-1; 
	else if (v1!=v1 || v2!=v2) top.s1.i=-1; 
	else top.s1.i=0; 
ENDCODE
#endif

/**
 * dconst_0 (0e) <br>
 *	==> 0.0<br>
 *	pushes the constant 0.0 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DCONST_0)
BEGCODE(DCONST_0)
	CONSTRAIN0(0);
	pushTop();
	top.d=DOUBLE2TD(bc&7);
	PUSHTOP0();
ENDCODE
#endif

/**
 * dconst_1 (0f) <br>
 *	==> 1.0<br>
 *	pushes the constant 1.0 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DCONST_1)
BEGCODE(DCONST_1)
	CONSTRAIN0(1);
	pushTop();
	top.d=DOUBLE2TD(bc&7);
	PUSHTOP0();
ENDCODE
#endif

/**
 * ddiv (6f) <br>
 *	value1, value2 ==> result<br>
 *	divides two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DDIV)
BEGCODE(DDIV)
	popTop0();	
	top.d=DOUBLE2TD(TD2DOUBLE(popp2()->d)/TD2DOUBLE(top.d));
	PUSHTOP0();
ENDCODE
#endif

/**
 * dload (18) 1: index<br>
 *	==> value<br>
 *	loads a double value from a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DLOAD)
BEGCODE(DLOAD)
	pushTop();
	top.d= getLocal(GETCODEBYTE2())->d;
	PUSHTOP0();
ENDCODE
#endif

/**
 * dload_0 (26) <br>
 *	==> value<br>
 *	loads a double from local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DLOAD_0)
BEGCODE(DLOAD_0)
	CONSTRAIN0(0);
	pushTop();
	top.d= getLocal(bc&7)->d;
	PUSHTOP0();
ENDCODE
#endif

/**
 * dload_1 (27) <br>
 *	==> value<br>
 *	loads a double from local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DLOAD_1)
BEGCODE(DLOAD_1)
	CONSTRAIN0(1);
	pushTop();
	top.d= getLocal(bc&7)->d;
	PUSHTOP0();
ENDCODE
#endif

/**
 * dload_2 (28) <br>
 *	==> value<br>
 *	loads a double from local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DLOAD_2)
BEGCODE(DLOAD_2)
	CONSTRAIN0(2);
	pushTop();
	top.d= getLocal(bc&7)->d;
	PUSHTOP0();
ENDCODE
#endif

/**
 * dload_3 (29) <br>
 *	==> value<br>
 *	loads a double from local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DLOAD_3)
BEGCODE(DLOAD_3)
	CONSTRAIN0(3);
	pushTop();
	top.d= getLocal(bc&7)->d;
	PUSHTOP0();
ENDCODE
#endif

/**
 * dmul (6b) <br>
 *	value1, value2 ==> result<br>
 *	multiplies two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DMUL)
BEGCODE(DMUL)
	popTop0();	
	top.d=DOUBLE2TD(TD2DOUBLE(popp2()->d)*TD2DOUBLE(top.d));
	PUSHTOP0();
ENDCODE
#endif

/**
 * dneg (77) <br>
 *	value ==> result<br>
 *	negates a double<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DNEG)
BEGCODE(DNEG)
	popTop0();	
	top.d=DOUBLE2TD(-TD2DOUBLE(top.d));
	PUSHTOP0();
ENDCODE
#endif

/**
 * drem (73) <br>
 *	value1, value2 ==> result<br>
 *	gets the remainder from a division between two doubles<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DREM)
BEGCODE(DREM)
	double d0, d1;
	jlong j;
	popTop0();	
	d0=TD2DOUBLE(popp2()->d);
	d1= d0 / TD2DOUBLE(top.d);
	j=d1;
	top.d=DOUBLE2TD(d0-j*top.d);
	PUSHTOP0();
ENDCODE
#endif

/**
 * dreturn (af) <br>
 *	value ==> [empty]<br>
 *	returns a double from a method<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DRETURN)
BEGCODE(DRETURN)
	popTop0();
	areturn();
	PUSHTOP0();
ENDCODE
#endif

/**
 * dstore (39) 1: index<br>
 *	value ==><br>
 *	stores a double value into a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSTORE)
BEGCODE(DSTORE)
	popTop0();
	getLocal(GETCODEBYTE2())->d=top.d;
	popTop();
ENDCODE
#endif

/**
 * dstore_0 (47) <br>
 *	value ==><br>
 *	stores a double into local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSTORE_0)
BEGCODE(DSTORE_0)
	CONSTRAIN0(0);
	popTop0();
	getLocal(bc&7)->d=top.d;
	popTop();
ENDCODE
#endif

/**
 * dstore_1 (48) <br>
 *	value ==><br>
 *	stores a double into local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSTORE_1)
BEGCODE(DSTORE_1)
	CONSTRAIN0(1);
	popTop0();
	getLocal(bc&7)->d=top.d;
	popTop();
ENDCODE
#endif

/**
 * dstore_2 (49) <br>
 *	value ==><br>
 *	stores a double into local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSTORE_2)
BEGCODE(DSTORE_2)
	CONSTRAIN0(2);
	popTop0();
	getLocal(bc&7)->d=top.d;
	popTop();
ENDCODE
#endif

/**
 * dstore_3 (4a) <br>
 *	value ==><br>
 *	stores a double into local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSTORE_3)
BEGCODE(DSTORE_3)
	CONSTRAIN0(3);
	popTop0();
	getLocal(bc&7)->d=top.d;
	popTop();
ENDCODE
#endif

/**
 * dsub (67) <br>
 *	value1, value2 ==> result<br>
 *	subtracts a double from another<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DSUB)
BEGCODE(DSUB)
	popTop0();	
	top.d=DOUBLE2TD(TD2DOUBLE(popp2()->d)-TD2DOUBLE(top.d));
	PUSHTOP0();
ENDCODE
#endif

/**
 * dup (59) <br>
 *	value ==> value, value<br>
 *	duplicates the value on top of the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP)
BEGCODE(DUP)
	pushTop();
ENDCODE
#endif

/**
 * dup_x1 (5a) <br>
 *	value2, value1 ==> value1, value2, value1<br>
 *	inserts a copy of the top value into the stack two values from the top.s1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP_X1)
BEGCODE(DUP_X1)
	popTop0();
	pushTop();
	pushTop0();
ENDCODE
#endif

/**
 * dup_x2 (5b) <br>
 *	value3, value2, value1 ==> value1, value3, value2, value1<br>
 *	inserts a copy of the top value into the stack two (if value2 is double or long it takes up the entry of value3, too) or three values (if value2 is neither double nor long) from the top.s1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP_X2)
BEGCODE(DUP_X2)
	stack_t value2= *pop();
	stack_t value3= *pop();

	pushTop();
	*push()=value3;
	*push()=value2;
ENDCODE
#endif

/**
 * dup2 (5c) <br>
 *	{value2, value1} ==> {value2, value1}, {value2, value1}<br>
 *	duplicate top.s1 two stack words (two values, if value1 is not double nor long; a single value, if value1 is double or long)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP2)
BEGCODE(DUP2)
	top.s0= *(dataSp-1);
	pushTop();
	pushTop0();
ENDCODE
#endif

/**
 * dup2_x1 (5d) <br>
 *	value3, {value2, value1} ==> {value2, value1}, value3, {value2, value1}<br>
 *	duplicate two words and insert beneath third word (see explanation above)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP2_X1)
BEGCODE(DUP2_X1)
	stack_t value2= *pop();
	stack_t value3= *pop();

	*push()=value2;
	pushTop();

	*push()=value3;

	*push()=value2;
ENDCODE
#endif

/**
 * dup2_x2 (5e) <br>
 *	{value4, value3}, {value2, value1} ==> {value2, value1}, {value4, value3}, {value2, value1}<br>
 *	duplicate two words and insert beneath fourth word<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_DUP2_X2)
BEGCODE(DUP2_X2)
	stack_t value2= *pop();
	stack_t value3= *pop();
	stack_t value4= *pop();

	*push()=value2;
	pushTop();

	*push()=value4;
	*push()=value3;

	*push()=value2;
ENDCODE
#endif

/**
 * f2d (8d) <br>
 *	value ==> result<br>
 *	converts a float to a double<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_F2D)
BEGCODE(F2D)
	top.d=DOUBLE2TD(TF2FLOAT(top.s1.f));
	PUSHTOP0();
ENDCODE
#endif

/**
 * f2i (8b) <br>
 *	value ==> result<br>
 *	converts a float to an int<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_F2I)
BEGCODE(F2I)
	top.s1.i=TF2FLOAT(top.s1.f);
ENDCODE
#endif

/**
 * f2l (8c) <br>
 *	value ==> result<br>
 *	converts a float to a long<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_F2L)
BEGCODE(F2L)
	top.j=TF2FLOAT(top.s1.f);
	PUSHTOP0();
ENDCODE
#endif

/**
 * fadd (62) <br>
 *	value1, value2 ==> result<br>
 *	adds two floats<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FADD)
BEGCODE(FADD)
	top.s1.f=FLOAT2TF(TF2FLOAT(top.s1.f) + TF2FLOAT(fpop()));
ENDCODE
#endif

/**
 * faload (30) <br>
 *	arrayref, index ==> value<br>
 *	loads a float from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FALOAD)
BEGCODE(FALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index))	top.s1.f=((jfloatArray)adr)->array[index];
ENDCODE
#endif

/**
 * fastore (51) <br>
 *	arrayref, index, value ==><br>
 *	stores a float in an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FASTORE)
BEGCODE(FASTORE)
	jfloat value=top.s1.f;
	jint index=ipop();
	jobject adr=apop();
	popTop();
	if (checkIndex(adr, index))	((jfloatArray)adr)->array[index]=value;
ENDCODE
#endif

/**
 * fcmpg (96) <br>
 *	value1, value2 ==> result<br>
 *	compares two floats<br>
 * If the two numbers are the same, the integer 0 is pushed onto the stack. 
 * If value2 is greater than value1, the integer 1 is pushed onto the stack. 
 * If value1 is greater than value2, the integer -1 is pushed onto the stack. 
 * If either number is NaN, the integer 1 is pushed onto the stack. +0.0 and -0.0 are treated as equal.
 */
#if ALL_BYTECODES || defined(HBC_DEF_FCMPG)
BEGCODE(FCMPG)
	float f2=TF2FLOAT(top.s1.f);
	float f1=TF2FLOAT(fpop());
	if (f2<f1) top.s1.i=1; 
	else if (f2>f1) top.s1.i=-1; 
	else if (f1!=f1 || f2!=f2) top.s1.i=1; 
	else top.s1.i=0; 
ENDCODE
#endif

/**
 * fcmpl (95) <br>
 *	value1, value2 ==> result<br>
 *	compares two floats<br>
 * If the two numbers are the same, the integer 0 is pushed onto the stack. 
 If value2 is greater than value1, the integer 1 is pushed onto the stack. 
 If value1 is greater than value2, the integer -1 is pushed onto the stack. 
 If either number is NaN, the integer -1 is pushed onto the stack. +0.0 and -0.0 are treated as equal.


 */
#if ALL_BYTECODES || defined(HBC_DEF_FCMPL)
BEGCODE(FCMPL)
	float f2=TF2FLOAT(top.s1.f);
	float f1=TF2FLOAT(fpop());
	if (f2<f1) top.s1.i=1; 
	else if (f2>f1) top.s1.i=-1; 
	else if (f1!=f1 || f2!=f2) top.s1.i=-1; 
	else top.s1.i=0; 
ENDCODE
#endif

/**
 * fconst_0 (0b) <br>
 *	==> 0.0f<br>
 *	pushes 0.0f on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FCONST_0)
BEGCODE(FCONST_0)
	CONSTRAIN0(0);
	pushTop();
	top.s1.f=FLOAT2TF(bc&7);
ENDCODE
#endif

/**
 * fconst_1 (0c) <br>
 *	==> 1.0f<br>
 *	pushes 1.0f on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FCONST_1)
BEGCODE(FCONST_1)
	CONSTRAIN0(1);
	pushTop();
	top.s1.f=FLOAT2TF(bc&7);
ENDCODE
#endif

/**
 * fconst_2 (0d) <br>
 *	==> 2.0f<br>
 *	pushes 2.0f on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FCONST_2)
BEGCODE(FCONST_2)
	CONSTRAIN0(2);
	pushTop();
	top.s1.f=FLOAT2TF(bc&7);
ENDCODE
#endif

/**
 * fdiv (6e) <br>
 *	value1, value2 ==> result<br>
 *	divides two floats<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FDIV)
BEGCODE(FDIV)
	top.s1.f=FLOAT2TF(TF2FLOAT(fpop()) / TF2FLOAT(top.s1.f));
ENDCODE
#endif

/**
 * fload (17) 1: index<br>
 *	==> value<br>
 *	loads a float value from a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FLOAD)
BEGCODE(FLOAD)
	pushTop();
	top.s1=getLocal(GETCODEBYTE2())->s0;
ENDCODE
#endif

/**
 * fload_0 (22) <br>
 *	==> value<br>
 *	loads a float value from local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FLOAD_0)
BEGCODE(FLOAD_0)
	CONSTRAIN0(0);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
ENDCODE
#endif

/**
 * fload_1 (23) <br>
 *	==> value<br>
 *	loads a float value from local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FLOAD_1)
BEGCODE(FLOAD_1)
	CONSTRAIN0(1);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
ENDCODE
#endif

/**
 * fload_2 (24) <br>
 *	==> value<br>
 *	loads a float value from local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FLOAD_2)
BEGCODE(FLOAD_2)
	CONSTRAIN0(2);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
ENDCODE
#endif

/**
 * fload_3 (25) <br>
 *	==> value<br>
 *	loads a float value from local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FLOAD_3)
BEGCODE(FLOAD_3)
	CONSTRAIN0(3);
	pushTop();
	top.s1=getLocal(bc&7)->s0;
ENDCODE
#endif

/**
 * fmul (6a) <br>
 *	value1, value2 ==> result<br>
 *	multiplies two floats<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FMUL)
BEGCODE(FMUL)
	top.s1.f=FLOAT2TF(TF2FLOAT(top.s1.f) * TF2FLOAT(fpop()));
ENDCODE
#endif

/**
 * fneg (76) <br>
 *	value ==> result<br>
 *	negates a float<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FNEG)
BEGCODE(FNEG)
	top.s1.f=FLOAT2TF(-TF2FLOAT(top.s1.f));
ENDCODE
#endif

/**
 * frem (72) <br>
 *	value1, value2 ==> result<br>
 *	gets the remainder from a division between two floats<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FREM)
BEGCODE(FREM)
	jfloat f0, f1;
	jint i;
	f0=TF2FLOAT(pop()->f);
	f1= f0 / TF2FLOAT(top.s1.f);
	i=f1;
	top.s1.f=FLOAT2TF(f0-i*top.s1.f);
ENDCODE
#endif

/**
 * freturn (ae) <br>
 *	value ==> [empty]<br>
 *	returns a float<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FRETURN)
BEGCODE(FRETURN)
	areturn();
ENDCODE
#endif

/**
 * fstore (38) 1: index<br>
 *	value ==><br>
 *	stores a float value into a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSTORE)
BEGCODE(FSTORE)
	setLocal(GETCODEBYTE2(), &top.s1);
	popTop();
ENDCODE
#endif

/**
 * fstore_0 (43) <br>
 *	value ==><br>
 *	stores a float value into local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSTORE_0)
BEGCODE(FSTORE_0)
	CONSTRAIN0(0);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * fstore_1 (44) <br>
 *	value ==><br>
 *	stores a float value into local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSTORE_1)
BEGCODE(FSTORE_1)
	CONSTRAIN0(1);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * fstore_2 (45) <br>
 *	value ==><br>
 *	stores a float value into local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSTORE_2)
BEGCODE(FSTORE_2)
	CONSTRAIN0(2);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * fstore_3 (46) <br>
 *	value ==><br>
 *	stores a float value into local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSTORE_3)
BEGCODE(FSTORE_3)
	CONSTRAIN0(3);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * fsub (66) <br>
 *	value1, value2 ==> result<br>
 *	subtracts two floats<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_FSUB)
BEGCODE(FSUB)
	top.s1.f=FLOAT2TF(TF2FLOAT(fpop()) - TF2FLOAT(top.s1.f));
ENDCODE
#endif

/**
 * getfield (b4) 2: index1, index2<br>
 *	objectref ==> value<br>
 *	gets a field value of an object objectref, where the field is identified by field reference in the constant pool index (index1 << 8 + index2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_L)
BEGCODE(GETFIELD_L)
#if	HAIKU_PanicExceptions & NullPointerException
	jobject* addr=(jobject*)getField();
	if (addr!=NULL) {
		top.s1.a=*addr;
		setMarkBit(top.s1.a);
	}
#else
	top.s1.a=*(jobject*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_B)
BEGCODE(GETFIELD_B)
#if	HAIKU_PanicExceptions & NullPointerException
	jbyte* addr=(jbyte*)getField();
	if (addr!=NULL) top.s1.i=*addr;
#else
	top.s1.i=*(jbyte*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_C)
BEGCODE(GETFIELD_C)
#if	HAIKU_PanicExceptions & NullPointerException
	jchar* addr=(jchar*)getField();
	if (addr!=NULL) top.s1.i=*addr;
#else
	top.s1.i=*(jchar*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_D)
BEGCODE(GETFIELD_D)
#if	HAIKU_PanicExceptions & NullPointerException
	jdouble* addr=(jdouble*)getField();
	if (addr!=NULL) {
		top.d=*addr;
		PUSHTOP0();
	}
#else
	top.d=*(jdouble*)getField();
	PUSHTOP0();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_F)
BEGCODE(GETFIELD_F)
#if	HAIKU_PanicExceptions & NullPointerException
	jfloat* addr=(jfloat*)getField();
	if (addr!=NULL) top.s1.f=*addr;
#else
	top.s1.f=*(jfloat*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_I)
BEGCODE(GETFIELD_I)
#if	HAIKU_PanicExceptions & NullPointerException
	jint* addr=(jint*)getField();
	if (addr!=NULL) top.s1.i=*addr;
#else
	top.s1.i=*(jint*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_J)
BEGCODE(GETFIELD_J)
#if	HAIKU_PanicExceptions & NullPointerException
	jlong* addr=(jlong*)getField();
	if (addr!=NULL) {
		top.j=*addr;
		PUSHTOP0();
	}
#else
	top.j=*(jlong*)getField();
	PUSHTOP0();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_S)
BEGCODE(GETFIELD_S)
#if	HAIKU_PanicExceptions & NullPointerException
	jshort* addr=(jshort*)getField();
	if (addr!=NULL) top.s1.i=*addr;
#else
	top.s1.i=*(jshort*)getField();
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETFIELD_Z)
BEGCODE(GETFIELD_Z)
#if	HAIKU_PanicExceptions & NullPointerException
	jboolean* addr=(jboolean*)getField();
	if (addr!=NULL) top.s1.i=*addr;
#else
	top.s1.i=*(jboolean*)getField();
#endif
ENDCODE
#endif

/**
 * getstatic (b2) 2: index1, index2<br>
 *	==> value<br>
 *	gets a static field value of a class, where the field is identified by field reference in the constant pool index (index1 << 8 + index2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_L)
BEGCODE(GETSTATIC_L)
	pushTop();
	top.s1.a=*(jobject*)GETCODEADR2();
	setMarkBit(top.s1.a);
ENDCODE
#endif

/**
 * getstatic (b2) 2: index1, index2<br>
 *	==> value<br>
 *	gets a native static C struct
 */
#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_N)
BEGCODE(GETSTATIC_N)
	pushTop();
	top.s1.a=(jobject*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_B)
BEGCODE(GETSTATIC_B)
	pushTop();
	top.s1.i=*(jbyte*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_C)
BEGCODE(GETSTATIC_C)
	pushTop();
	top.s1.i=*(jchar*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_D)
BEGCODE(GETSTATIC_D)
	pushTop();
	top.d=*(jdouble*)GETCODEADR2();
	PUSHTOP0();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_F)
BEGCODE(GETSTATIC_F)
	pushTop();
	top.s1.f=*(jfloat*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_I)
BEGCODE(GETSTATIC_I)
	pushTop();
	top.s1.i=*(jint*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_J)
BEGCODE(GETSTATIC_J)
	pushTop();
	top.j=*(jlong*)GETCODEADR2();
	PUSHTOP0();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_S)
BEGCODE(GETSTATIC_S)
	pushTop();
	top.s1.i=*(jshort*)GETCODEADR2();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_GETSTATIC_Z)
BEGCODE(GETSTATIC_Z)
	pushTop();
	top.s1.i=*(jboolean*)GETCODEADR2();
ENDCODE
#endif

/**
 * goto (a7) 2: branchbyte1, branchbyte2<br>
 *	[no change]<br>
 *	goes to another instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_GOTO)
BEGCODE(GOTO)
	pc=(unsigned char *)GETCODEADR2();
	LOOP_THREAD();
ENDCODE
#endif

/**
 * goto_w (c8) 4: branchbyte1, branchbyte2, branchbyte3, branchbyte4<br>
 *	[no change]<br>
 *	goes to another instruction at branchoffset (signed int constructed from unsigned bytes branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_GOTO_W)
BEGCODE(GOTO_W)
	unimplemented();
ENDCODE
#endif

/**
 * i2b (91) <br>
 *	value ==> result<br>
 *	converts an int into a byte<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2B)
BEGCODE(I2B)
	top.s1.i= (jbyte)top.s1.i;
ENDCODE
#endif

/**
 * i2c (92) <br>
 *	value ==> result<br>
 *	converts an int into a character<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2C)
BEGCODE(I2C)
	top.s1.i= (jchar)top.s1.i;
ENDCODE
#endif

/**
 * i2d (87) <br>
 *	value ==> result<br>
 *	converts an int into a double<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2D)
BEGCODE(I2D)
	top.d= DOUBLE2TD(top.s1.i);
	PUSHTOP0();
ENDCODE
#endif

/**
 * i2f (86) <br>
 *	value ==> result<br>
 *	converts an int into a float<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2F)
BEGCODE(I2F)
	top.s1.f= FLOAT2TF(top.s1.i);
ENDCODE
#endif

/**
 * i2l (85) <br>
 *	value ==> result<br>
 *	converts an int into a long<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2L)
BEGCODE(I2L)
	top.j= (jlong)top.s1.i; // without this AVR GCC leaves high bytes dirty
	PUSHTOP0();
ENDCODE
#endif

/**
 * i2s (93) <br>
 *	value ==> result<br>
 *	converts an int into a short<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_I2S)
BEGCODE(I2S)
top.s1.i= (jshort)top.s1.i;
ENDCODE
#endif

/**
 * iadd (60) <br>
 *	value1, value2 ==> result<br>
 *	adds two ints together<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IADD)
BEGCODE(IADD)
	top.s1.i+=ipop();
ENDCODE
#endif

/**
 * iaload (2e) <br>
 *	arrayref, index ==> value<br>
 *	loads an int from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IALOAD)
BEGCODE(IALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index))	top.s1.i=((jintArray)adr)->array[index];
ENDCODE
#endif

/**
 * iand (7e) <br>
 *	value1, value2 ==> result<br>
 *	performs a bitwise and on two integers<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IAND)
BEGCODE(IAND)
	top.s1.i&=ipop();
ENDCODE
#endif

/**
 * iastore (4f) <br>
 *	arrayref, index, value ==><br>
 *	stores an int into an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IASTORE)
BEGCODE(IASTORE)
	jint value=top.s1.i;
	jint index=ipop();
	jobject adr=apop();
	popTop();
	if (checkIndex(adr, index))	((jintArray)adr)->array[index]=value;
ENDCODE
#endif

/**
 * iconst_m1 (02) <br>
 *	==> -1<br>
 *	loads the int value -1 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_M1)
BEGCODE(ICONST_M1)
	pushTop();
	top.s1.i=-1;
ENDCODE
#endif

/**
 * iconst_0 (03) <br>
 *	==> 0<br>
 *	loads the int value 0 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_0)
BEGCODE(ICONST_0)
	pushTop();
	top.s1.i=0;
ENDCODE
#endif

/**
 * iconst_1 (04) <br>
 *	==> 1<br>
 *	loads the int value 1 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_1)
BEGCODE(ICONST_1)
	pushTop();
	top.s1.i=1;
ENDCODE
#endif

/**
 * iconst_2 (05) <br>
 *	==> 2<br>
 *	loads the int value 2 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_2)
BEGCODE(ICONST_2)
	pushTop();
	top.s1.i=2;
ENDCODE
#endif

/**
 * iconst_3 (06) <br>
 *	==> 3<br>
 *	loads the int value 3 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_3)
BEGCODE(ICONST_3)
	pushTop();
	top.s1.i=3;
ENDCODE
#endif

/**
 * iconst_4 (07) <br>
 *	==> 4<br>
 *	loads the int value 4 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_4)
BEGCODE(ICONST_4)
	pushTop();
	top.s1.i=4;
ENDCODE
#endif

/**
 * iconst_5 (08) <br>
 *	==> 5<br>
 *	loads the int value 5 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ICONST_5)
BEGCODE(ICONST_5)
	pushTop();
	top.s1.i=5;
ENDCODE
#endif

/**
 * idiv (6c) <br>
 *	value1, value2 ==> result<br>
 *	divides two integers<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IDIV)
BEGCODE(IDIV)
#if	HAIKU_PanicExceptions & ArithmeticException
	if ( top.s1.i==0 ) {
		ipop();
		panic(ArithmeticException, 0);
	} else {
		top.s1.i=ipop()/top.s1.i;
	}
#else
#if NO_MICRO
	if ( top.s1.i==0 ) {
		ipop();
		top.s1.i=-1;
	} else {
		top.s1.i=ipop()/top.s1.i;
	}
#else
	top.s1.i=ipop()/top.s1.i;
#endif
#endif
ENDCODE
#endif

/**
 * if_acmpeq (a5) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if references are equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ACMPEQ)
BEGCODE(IF_ACMPEQ)
	jobject v1=apop(), v2=top.s1.a;
	CONSTRAIN0(2);
	compare(v1!=v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_acmpne (a6) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if references are not equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ACMPNE)
BEGCODE(IF_ACMPNE)
	jobject v1=apop(), v2=top.s1.a;
	CONSTRAIN0(5);
	compare(v1!=v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmpeq (9f) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if ints are equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPEQ)
BEGCODE(IF_ICMPEQ)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(2);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmpne (a0) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if ints are not equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPNE)
BEGCODE(IF_ICMPNE)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(5);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmplt (a1) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if value1 is less than value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPLT)
BEGCODE(IF_ICMPLT)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(4);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmpge (a2) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if value1 is greater than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPGE)
BEGCODE(IF_ICMPGE)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(3);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmpgt (a3) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if value1 is greater than value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPGT)
BEGCODE(IF_ICMPGT)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(1);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * if_icmple (a4) 2: branchbyte1, branchbyte2<br>
 *	value1, value2 ==><br>
 *	if value1 is less than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IF_ICMPLE)
BEGCODE(IF_ICMPLE)
	jint v1=ipop(), v2=top.s1.i;
	CONSTRAIN0(6);
	compare(v1-v2);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifeq (99) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFEQ)
BEGCODE(IFEQ)
	CONSTRAIN0(2);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifne (9a) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is not 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFNE)
BEGCODE(IFNE)
	CONSTRAIN0(5);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * iflt (9b) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is less than 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFLT)
BEGCODE(IFLT)
	CONSTRAIN0(4);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifge (9c) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is greater than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFGE)
BEGCODE(IFGE)
	CONSTRAIN0(3);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifgt (9d) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is greater than 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFGT)
BEGCODE(IFGT)
	CONSTRAIN0(1);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifle (9e) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is less than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFLE)
BEGCODE(IFLE)
	CONSTRAIN0(6);
	compare(top.s1.i);
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifnonnull (c7) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is not null, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFNONNULL)
BEGCODE(IFNONNULL)
	CONSTRAIN0(5);
	compare((jint)top.s1.a); // TODO 64 bit pointer vs. 32bit int
	LOOP_THREAD();
ENDCODE
#endif

/**
 * ifnull (c6) 2: branchbyte1, branchbyte2<br>
 *	value ==><br>
 *	if value is null, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IFNULL)
BEGCODE(IFNULL)
	CONSTRAIN0(2);
	compare((jint)top.s1.a); // TODO 64 bit pointer vs. 32bit int
	LOOP_THREAD();
ENDCODE
#endif

/**
 * iinc (84) 2: index, const<br>
 *	[No change]<br>
 *	increment local variable #index by signed byte const<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IINC)
BEGCODE(IINC)
	jstack top_s1 =(jstack)getLocal(GETCODEBYTE2());
	top_s1->i+=(int8_t)GETCODEBYTE2();
ENDCODE
#endif

/**
 * HAIKU: iinc1 (84) 1: index<br>
 *	[No change]<br>
 *	increment local variable #index by 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IINC1)
BEGCODE(IINC1)
	jstack top_s1 =(jstack)getLocal(GETCODEBYTE2());
	top_s1->i++;
ENDCODE
#endif

/**
 * iload (15) 1: index<br>
 *	==> value<br>
 *	loads an int value from a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ILOAD)
BEGCODE(ILOAD)
	pushTop();
	top.s1.i=getLocal(GETCODEBYTE2())->s0.i;
ENDCODE
#endif

/**
 * iload_0 (1a) <br>
 *	==> value<br>
 *	loads an int value from local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ILOAD_0)
BEGCODE(ILOAD_0)
	pushTop();
	top.s1.i=lsp[0].i;
ENDCODE
#endif

/**
 * iload_1 (1b) <br>
 *	==> value<br>
 *	loads an int value from local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ILOAD_1)
BEGCODE(ILOAD_1)
	pushTop();
	top.s1.i=lsp[1].i;
ENDCODE
#endif

/**
 * iload_2 (1c) <br>
 *	==> value<br>
 *	loads an int value from local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ILOAD_2)
BEGCODE(ILOAD_2)
	pushTop();
	top.s1.i=lsp[2].i;
ENDCODE
#endif

/**
 * iload_3 (1d) <br>
 *	==> value<br>
 *	loads an int value from local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ILOAD_3)
BEGCODE(ILOAD_3)
	pushTop();
	top.s1.i=lsp[3].i;
ENDCODE
#endif

/**
 * imul (68) <br>
 *	value1, value2 ==> result<br>
 *	multiply two integers<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IMUL)
BEGCODE(IMUL)
	top.s1.i*=ipop();
ENDCODE
#endif

/**
 * ineg (74) <br>
 *	value ==> result<br>
 *	negate int<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_INEG)
BEGCODE(INEG)
	top.s1.i=-top.s1.i;
ENDCODE
#endif

/**
 * instanceof (c1) 2: indexbyte1, indexbyte2<br>
 *	objectref ==> result<br>
 *	determines if an object objectref is of a given type, identified by class reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_INSTANCEOF)
BEGCODE(INSTANCEOF)
	top.s1.i=instanceOf(top.s1.a, (jclass)GETCODEADR2());
ENDCODE
#endif

/**
 * invokeinterface (b9) 4: indexbyte1, indexbyte2, count, 0<br>
 *	objectref, [arg1, arg2, ...] ==><br>
 *	invokes an interface method on object objectref, where the interface method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_INVOKEINTERFACE)
BEGCODE(INVOKEINTERFACE)
	unsigned char * code;
	int pos=GETCODEBYTE2();
	int msg=GETCODEBYTE2();
	if (pos==0) code=top.s1.a; else code=(dataSp-pos)->a;
	pc+=2;
	code=getMethod(code, msg);
	invoke((ByteCode *)code);
ENDCODE
#endif

/**
 * invokespecial (b7) 2: indexbyte1, indexbyte2<br>
 *	objectref, [arg1, arg2, ...] ==><br>
 *	invoke instance method on object objectref, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_INVOKESPECIAL)
BEGCODE(INVOKESPECIAL)
	invoke((ByteCode *)GETCODEADR2());
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_INVOKESHORT)
BEGCODE(INVOKESHORT)
	invoke((ByteCode *)pgm_read_wordRef(*(functionTable+bc)));
ENDCODE
#endif

/**
 * invokestatic (b8) 2: indexbyte1, indexbyte2<br>
 *	[arg1, arg2, ...] ==><br>
 *	invoke a static method, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_INVOKESTATIC)
BEGCODE(INVOKESTATIC)
	invoke((ByteCode *)GETCODEADR2());
ENDCODE
#endif

/**
 * invokevirtual (b6) 2: indexbyte1, indexbyte2<br>
 *	objectref, [arg1, arg2, ...] ==><br>
 *	invoke virtual method on object objectref, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 * HAIKUVM
 *  indexbyte1 is used to store the max number of params of this method
 *  indexbyte2 is used to store the (global) method index [2(?), 255]
 */
#if ALL_BYTECODES || defined(HBC_DEF_INVOKEVIRTUAL)
BEGCODE(INVOKEVIRTUAL)
	jobject code;
	ByteCode * bytecode;
	int pos=GETCODEBYTE2();
	int msg=GETCODEBYTE2();
	pushTop(); dataSp--;
	code=(dataSp-pos)->a;
	bytecode=getMethod(code, msg);
	invoke((ByteCode *)bytecode);
ENDCODE
#endif

/**
 * ior (80) <br>
 *	value1, value2 ==> result<br>
 *	bitwise int or<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IOR)
BEGCODE(IOR)
	top.s1.i|=ipop();
ENDCODE
#endif

/**
 * irem (70) <br>
 *	value1, value2 ==> result<br>
 *	logical int remainder<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IREM)
BEGCODE(IREM)
	top.s1.i=ipop()%top.s1.i;
ENDCODE
#endif

/**
 * ireturn (ac) <br>
 *	value ==> [empty]<br>
 *	returns an integer from a method<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IRETURN)
BEGCODE(IRETURN)
	areturn();
ENDCODE
#endif

/**
 * ishl (78) <br>
 *	value1, value2 ==> result<br>
 *	int shift left<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISHL)
BEGCODE(ISHL)
	top.s1.i=ipop()<<top.s1.i;
ENDCODE
#endif

/**
 * ishr (7a) <br>
 *	value1, value2 ==> result<br>
 *	int arithmetic shift right<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISHR)
BEGCODE(ISHR)
	top.s1.i=ipop()>>top.s1.i;
ENDCODE
#endif

/**
 * istore (36) 1: index<br>
 *	value ==><br>
 *	store int value into variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISTORE)
BEGCODE(ISTORE)
	setLocal(GETCODEBYTE2(), &top.s1);
	popTop();
ENDCODE
#endif

/**
 * istore_0 (3b) <br>
 *	value ==><br>
 *	store int value into variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISTORE_0)
BEGCODE(ISTORE_0)
	CONSTRAIN0(0);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * istore_1 (3c) <br>
 *	value ==><br>
 *	store int value into variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISTORE_1)
BEGCODE(ISTORE_1)
	CONSTRAIN0(1);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * istore_2 (3d) <br>
 *	value ==><br>
 *	store int value into variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISTORE_2)
BEGCODE(ISTORE_2)
	CONSTRAIN0(2);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * istore_3 (3e) <br>
 *	value ==><br>
 *	store int value into variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISTORE_3)
BEGCODE(ISTORE_3)
	CONSTRAIN0(3);
	setLocal(bc&7, &top.s1);
	popTop();
ENDCODE
#endif

/**
 * isub (64) <br>
 *	value1, value2 ==> result<br>
 *	int subtract<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_ISUB)
BEGCODE(ISUB)
	top.s1.i=ipop()-top.s1.i;
ENDCODE
#endif

/**
 * iushr (7c) <br>
 *	value1, value2 ==> result<br>
 *	int logical shift right<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IUSHR)
BEGCODE(IUSHR)
	top.s1.i=((juint)ipop())>>top.s1.i;
ENDCODE
#endif

/**
 * ixor (82) <br>
 *	value1, value2 ==> result<br>
 *	int xor<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IXOR)
BEGCODE(IXOR)
	top.s1.i^=ipop();
ENDCODE
#endif

/**
 * jsr (a8) 2: branchbyte1, branchbyte2<br>
 *	==> address<br>
 *	jump to subroutine at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2) and place the return address on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_JSR)
BEGCODE(JSR)
	unimplemented();
ENDCODE
#endif

/**
 * jsr_w (c9) 4: branchbyte1, branchbyte2, branchbyte3, branchbyte4<br>
 *	==> address<br>
 *	jump to subroutine at branchoffset (signed int constructed from unsigned bytes branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4) and place the return address on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_JSR_W)
BEGCODE(JSR_W)
	unimplemented();
ENDCODE
#endif

/**
 * l2d (8a) <br>
 *	value ==> result<br>
 *	converts a long to a double<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_L2D)
BEGCODE(L2D)
	popTop0();
	top.d=DOUBLE2TD(1.0*top.j);
	PUSHTOP0();
ENDCODE
#endif

/**
 * l2f (89) <br>
 *	value ==> result<br>
 *	converts a long to a float<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_L2F)
BEGCODE(L2F)
	popTop0();
	top.s1.f=FLOAT2TF(top.j);
ENDCODE
#endif

/**
 * l2i (88) <br>
 *	value ==> result<br>
 *	converts a long to a int<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_L2I)
BEGCODE(L2I)
	popTop0();
	top.s1.i=(jint)top.j;
ENDCODE
#endif

/**
 * ladd (61) <br>
 *	value1, value2 ==> result<br>
 *	add two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LADD)
BEGCODE(LADD)
	popTop0();
	top.j+=popp2()->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * laload (2f) <br>
 *	arrayref, index ==> value<br>
 *	load a long from an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LALOAD)
BEGCODE(LALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index)) 	top.j=((jlongArray)adr)->array[index];
	PUSHTOP0();
ENDCODE
#endif

/**
 * land (7f) <br>
 *	value1, value2 ==> result<br>
 *	bitwise and of two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LAND)
BEGCODE(LAND)
	popTop0();
	top.j&=popp2()->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lastore (50) <br>
 *	arrayref, index, value ==><br>
 *	store a long to an array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LASTORE)
BEGCODE(LASTORE)
	jint index;
	jobject adr;
	jlong j;
	popTop0();
	j=top.j;
	index=ipop();
	adr=apop();
	popTop();
	if (checkIndex(adr, index)) ((jlongArray)adr)->array[index]=j;
ENDCODE
#endif

/**
 * lcmp (94) <br>
 *	value1, value2 ==> result<br>
 *	compares two longs values<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LCMP)
BEGCODE(LCMP)
	// it's wrong to write: top.s1.i=(int)(jpop()-top.s1.j); because high bytes are neglected
	jlong j;
	popTop0();
	j=popp2()->j-top.j;
	if (j>0) top.s1.i=1;
	else if (j<0) top.s1.i=-1;
	else top.s1.i=0;
ENDCODE
#endif

/**
 * lconst_0 (09) <br>
 *	==> 0L<br>
 *	pushes the long 0 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LCONST_0)
BEGCODE(LCONST_0)
	CONSTRAIN0(0);
	pushTop();
	top.j=0;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lconst_1 (0a) <br>
 *	==> 1L<br>
 *	pushes the long 1 onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LCONST_1)
BEGCODE(LCONST_1)
	CONSTRAIN0(1);
	pushTop();
	top.j=1;
	PUSHTOP0();
ENDCODE
#endif

/**
 * ldc (12) 1: index<br>
 *	==> value<br>
 *	pushes a constant #index from a constant pool (String, int or float) onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LDC_S)
BEGCODE(LDC_S)
	ldc_string();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_I)
BEGCODE(LDC_I)
	void* src = (void*)GETCODEADR2();
	pushTop();
	memcpy_P(&top.s1.i, src, sizeof(jint));
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_C)
BEGCODE(LDC_C)
	pushTop();
	top.s1.a=(jobject)GETCODEADR2();
	//Todo setMarkBit(top.s1.a); but for now classes reside in flash only
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_F)
BEGCODE(LDC_F)
	ldc_jfloat_t c;
	pushTop();
	// I tried a lot and found this solution: top.s1.fl to get the right float format out of the ROM on AVR.
	c.fl=pgm_read_dwordRef(((ldc_jfloat_t*)GETCODEADR2())->fl);
	top.s1.f=FLOAT2TF(c.f);
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_FX)
BEGCODE(LDC_FX)
	float n=0.0;
	pushTop();
	top.s1.f=FLOAT2TF((GETCODEBYTE2()-1)/n);
ENDCODE
#endif

/**
 * ldc_w (13) 2: indexbyte1, indexbyte2<br>
 *	==> value<br>
 *	pushes a constant #index from a constant pool (String, int or float) onto the stack (wide index is constructed as indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LDC_W_S)
BEGCODE(LDC_W_S)
	ldc_string();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_W_I)
BEGCODE(LDC_W_I)
	void* src = (void*)GETCODEADR2();
	pushTop();
	memcpy_P(&top.s1.i, src, sizeof(jint));
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_W_F)
BEGCODE(LDC_W_F)	
	ldc_jfloat_t c;
	pushTop();
	// I tried a lot and found this solution: top.s1.fl to get the right float format out of the ROM on AVR.
	c.fl=pgm_read_dwordRef(((ldc_jfloat_t*)GETCODEADR2())->fl);
	top.s1.f=FLOAT2TF(c.f);
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC_W_FX)
BEGCODE(LDC_W_FX)
	float n=0.0;
	pushTop();
	top.s1.f=FLOAT2TF((GETCODEBYTE2()-1)/n);
ENDCODE
#endif


/**
 * ldc2_w (14) 2: indexbyte1, indexbyte2<br>
 *	==> value<br>
 *	pushes a constant #index from a constant pool (double or long) onto the stack (wide index is constructed as indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LDC2_W_D)
BEGCODE(LDC2_W_D)
	ldc_jdouble_t c;
	pushTop();
	// I tried a lot and found this solution: top.s1.fl to get the right float format out of the ROM on AVR.
	c.dl=pgm_read_dwordRef(((ldc_jdouble_t*)GETCODEADR2())->dl);
	top.d=DOUBLE2TD(c.d);
	PUSHTOP0();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC2_W_DX)
BEGCODE(LDC2_W_DX)
	double n=0.0;
	pushTop();
	top.d=DOUBLE2TD((GETCODEBYTE2()-1)/n);
	PUSHTOP0();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_LDC2_W_L)
BEGCODE(LDC2_W_L)	
	void* src = (void*)GETCODEADR2();
	pushTop();
	memcpy_P(&top.j, src, sizeof(jlong));
	PUSHTOP0();
ENDCODE
#endif

/**
 * ldiv (6d) <br>
 *	value1, value2 ==> result<br>
 *	divide two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LDIV)
BEGCODE(LDIV)
	popTop0();
#if	HAIKU_PanicExceptions & ArithmeticException
	if ( top.j==0 ) {
		popp2();
		PUSHTOP0();
		panic(ArithmeticException, 0);
	} else {
		top.j=popp2()->j/top.j;
		PUSHTOP0();
	}
#else
#if NO_MICRO
	if ( top.j==0 ) {
		popp2();
		top.j=-1;
	} else {
		top.j=popp2()->j/top.j;
	}
#else
	top.j=popp2()->j/top.j;
#endif
	PUSHTOP0();
#endif
ENDCODE
#endif

/**
 * lload (16) 1: index<br>
 *	==> value<br>
 *	load a long value from a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LLOAD)
BEGCODE(LLOAD)
	pushTop();
	top.j=getLocal(GETCODEBYTE2())->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lload_0 (1e) <br>
 *	==> value<br>
 *	load a long value from a local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LLOAD_0)
BEGCODE(LLOAD_0)
	CONSTRAIN0(0);
	pushTop();
	top.j=getLocal(bc&7)->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lload_1 (1f) <br>
 *	==> value<br>
 *	load a long value from a local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LLOAD_1)
BEGCODE(LLOAD_1)
	CONSTRAIN0(1);
	pushTop();
	top.j=getLocal(bc&7)->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lload_2 (20) <br>
 *	==> value<br>
 *	load a long value from a local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LLOAD_2)
BEGCODE(LLOAD_2)
	CONSTRAIN0(2);
	pushTop();
	top.j=getLocal(bc&7)->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lload_3 (21) <br>
 *	==> value<br>
 *	load a long value from a local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LLOAD_3)
BEGCODE(LLOAD_3)
	CONSTRAIN0(3);
	pushTop();
	top.j=getLocal(bc&7)->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lmul (69) <br>
 *	value1, value2 ==> result<br>
 *	multiplies two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LMUL)
BEGCODE(LMUL)
	popTop0();
	top.j*=popp2()->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lneg (75) <br>
 *	value ==> result<br>
 *	negates a long<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LNEG)
BEGCODE(LNEG)
	popTop0();
	top.j= -top.j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lookupswitch (ab) 4+: <0-3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs...<br>
 *	key ==><br>
 *	a target address is looked up from a table using a key and execution continues from the instruction at that address<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LOOKUPSWITCH)
BEGCODE(LOOKUPSWITCH)
	int i;
	jint index=top.s1.i;
	void * dflt;
	int32_t npairs;
	popTop();

	dflt=(void*)GETCODEADR2();
	npairs=GETCODEDWORD2();
	for(i=0; i<npairs; i++) {
		if (index==(jint)GETCODEDWORD2()) {
			dflt=(void*)GETCODEADR2();
			break;
		} else {
			pc+=sizeof(void *);
		}
	}
	pc=dflt;
ENDCODE
#endif

/**
 * lor (81) <br>
 *	value1, value2 ==> result<br>
 *	bitwise or of two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LOR)
BEGCODE(LOR)
	popTop0();
	top.j|=popp2()->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lrem (71) <br>
 *	value1, value2 ==> result<br>
 *	remainder of division of two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LREM)
BEGCODE(LREM)
	popTop0();
	top.j=popp2()->j%top.j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lreturn (ad) <br>
 *	value ==> [empty]<br>
 *	returns a long value<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LRETURN)
BEGCODE(LRETURN)
	popTop0();
	areturn();
	PUSHTOP0();
ENDCODE
#endif

/**
 * lshl (79) <br>
 *	value1, value2 ==> result<br>
 *	bitwise shift left of a long value1 by int value2 positions<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSHL)
BEGCODE(LSHL)
	top.j=popp2()->j<<top.s1.i;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lshr (7b) <br>
 *	value1, value2 ==> result<br>
 *	bitwise shift right of a long value1 by int value2 positions<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSHR)
BEGCODE(LSHR)
	top.j=popp2()->j>>top.s1.i;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lstore (37) 1: index<br>
 *	value ==><br>
 *	store a long value in a local variable #index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSTORE)
BEGCODE(LSTORE)
	popTop0();
	getLocal(GETCODEBYTE2())->j=top.j;
	popTop();
ENDCODE
#endif

/**
 * lstore_0 (3f) <br>
 *	value ==><br>
 *	store a long value in a local variable 0<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSTORE_0)
BEGCODE(LSTORE_0)
	CONSTRAIN0(0);
	popTop0();
	getLocal(bc&7)->j=top.j;
	popTop();
ENDCODE
#endif

/**
 * lstore_1 (40) <br>
 *	value ==><br>
 *	store a long value in a local variable 1<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSTORE_1)
BEGCODE(LSTORE_1)
	CONSTRAIN0(1);
	popTop0();
	getLocal(bc&7)->j=top.j;
	popTop();
ENDCODE
#endif

/**
 * lstore_2 (41) <br>
 *	value ==><br>
 *	store a long value in a local variable 2<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSTORE_2)
BEGCODE(LSTORE_2)
	CONSTRAIN0(2);
	popTop0();
	getLocal(bc&7)->j=top.j;
	popTop();
ENDCODE
#endif

/**
 * lstore_3 (42) <br>
 *	value ==><br>
 *	store a long value in a local variable 3<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSTORE_3)
BEGCODE(LSTORE_3)
	CONSTRAIN0(3);
	popTop0();
	getLocal(bc&7)->j=top.j;
	popTop();
ENDCODE
#endif

/**
 * lsub (65) <br>
 *	value1, value2 ==> result<br>
 *	subtract two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LSUB)
BEGCODE(LSUB)
	popTop0();
	top.j=popp2()->j-top.j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lushr (7d) <br>
 *	value1, value2 ==> result<br>
 *	bitwise shift right of a long value1 by int value2 positions, unsigned<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LUSHR)
BEGCODE(LUSHR)
	top.j=((julong)(popp2()->j))>>top.s1.i;
	PUSHTOP0();
ENDCODE
#endif

/**
 * lxor (83) <br>
 *	value1, value2 ==> result<br>
 *	bitwise exclusive or of two longs<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_LXOR)
BEGCODE(LXOR)
	popTop0();
	top.j^=popp2()->j;
	PUSHTOP0();
ENDCODE
#endif

/**
 * monitorenter (c2) <br>
 *	objectref ==><br>
 *	enter monitor for object ("grab the lock" - start of synchronized() section)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_MONITORENTER)
BEGCODE(MONITORENTER)
	monitorenter();
ENDCODE
#endif

/**
 * monitorexit (c3) <br>
 *	objectref ==><br>
 *	exit monitor for object ("release the lock" - end of synchronized() section)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_MONITOREXIT)
BEGCODE(MONITOREXIT)
	monitorexit();
ENDCODE
#endif

/**
 * multianewarray (c5) 3: indexbyte1, indexbyte2, dimensions<br>
 * HAIKU: multianewarray (c5) 3: sizebyte, dimensions<br>
 *	count1, [count2,...] ==> arrayref<br>
 *	create a new array of dimensions dimensions with elements of type identified by class reference in constant pool index (indexbyte1 << 8 + indexbyte2); the sizes of each dimension is identified by count1, [count2, etc.]<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_MULTIANEWARRAY)
BEGCODE(MULTIANEWARRAY)
	jclass clazz= GETCODEADR2(); // base class
	int dim= GETCODEBYTE2();

	pushTop();
	multiArray((jobjectArray*)&top.s1.a, dim, clazz);
	dataSp-=dim;
ENDCODE
#endif

/**
 * new (bb) 2: indexbyte1, indexbyte2<br>
 *	==> objectref<br>
 *	creates new object of type identified by class reference in constant pool index (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_NEW)
BEGCODE(NEW)
	pushTop();
	{
		void * mem = newInstance((jclass)GETCODEADR2());
		PanicAware(mem, top.s1.a=mem);
	}
ENDCODE
#endif

/**
 * newarray (bc) 1: atype<br>
 * HAIKU: newarray (bc) 1: sizeindex<br>
 *	count ==> arrayref<br>
 *	creates new array with count elements of primitive type identified by atype<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_NEWARRAY)
BEGCODE(NEWARRAY)
	void * mem = newarray(top.s1.i, GETCODEADR2());
	PanicAware(mem, top.s1.a=mem);
ENDCODE
#endif

/**
 * nop (00) <br>
 *	[No change]<br>
 *	performs no operation<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_NOP)
BEGCODE(NOP)
ENDCODE
#endif

/**
 * pop (57) <br>
 *	value ==><br>
 *	discards the top.s1 value on the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_POP)
BEGCODE(POP)
	popTop();
ENDCODE
#endif

/**
 * pop2 (58) <br>
 *	{value2, value1} ==><br>
 *	discards the top.s1 two values on the stack (or one value, if it is a double or long)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_POP2)
BEGCODE(POP2)
	dataSp--;
	popTop();
ENDCODE
#endif

/**
 * putfield (b5) 2: indexbyte1, indexbyte2<br>
 *	objectref, value ==><br>
 *	set field to value in an object objectref, where the field is identified by a field reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_B)
BEGCODE(PUTFIELD_B)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jbyte*)(obj+GETCODEWORD2())=(jbyte)top.s1.i;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_C)
BEGCODE(PUTFIELD_C)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jchar*)(obj+GETCODEWORD2())=(jchar)top.s1.i;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_D)
BEGCODE(PUTFIELD_D)
	char* obj;
	popTop0();
	obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jdouble*)(obj+GETCODEWORD2())=top.d;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_F)
BEGCODE(PUTFIELD_F)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jfloat*)(obj+GETCODEWORD2())=top.s1.f;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_I)
BEGCODE(PUTFIELD_I)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jint*)(obj+GETCODEWORD2())=top.s1.i;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_J)
BEGCODE(PUTFIELD_J)
	char* obj;
	popTop0();
	obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jlong*)(obj+GETCODEWORD2())=top.j;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_L)
BEGCODE(PUTFIELD_L)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jobject*)(obj+GETCODEWORD2())=top.s1.a;
	setMarkBit(top.s1.a);
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_S)
BEGCODE(PUTFIELD_S)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jshort*)(obj+GETCODEWORD2())=(jshort)top.s1.i;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTFIELD_Z)
BEGCODE(PUTFIELD_Z)
	char* obj=cppop();
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==NULL) {
		panic(NullPointerException, 0);
	} else {
#endif
	*(jboolean*)(obj+GETCODEWORD2())=(jboolean)top.s1.i;
	popTop();
#if	HAIKU_PanicExceptions & NullPointerException
	}
#endif
ENDCODE
#endif

/**
 * putstatic (b3) 2: indexbyte1, indexbyte2<br>
 *	value ==><br>
 *	set static field to value in a class, where the field is identified by a field reference index in constant pool (indexbyte1 << 8 + indexbyte2)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_B)
BEGCODE(PUTSTATIC_B)
	*(jbyte*)GETCODEADR2()=(jbyte)top.s1.i;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_C)
BEGCODE(PUTSTATIC_C)
	*(jchar*)GETCODEADR2()=(jchar)top.s1.i;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_D)
BEGCODE(PUTSTATIC_D)
	popTop0();
	*(jdouble*)GETCODEADR2()=top.d;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_F)
BEGCODE(PUTSTATIC_F)
	*(jfloat*)GETCODEADR2()=top.s1.f;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_I)
BEGCODE(PUTSTATIC_I)
	*(jint*)GETCODEADR2()=top.s1.i;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_J)
BEGCODE(PUTSTATIC_J)
	popTop0();
	*(jlong*)GETCODEADR2()=top.j;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_L)
BEGCODE(PUTSTATIC_L)
	*(jobject*)GETCODEADR2()=top.s1.a;
	setMarkBit(top.s1.a);
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_S)
BEGCODE(PUTSTATIC_S)
	*(jshort*)GETCODEADR2()=(jshort)top.s1.i;
	popTop();
ENDCODE
#endif

#if ALL_BYTECODES || defined(HBC_DEF_PUTSTATIC_Z)
BEGCODE(PUTSTATIC_Z)
	*(jboolean*)GETCODEADR2()= (jboolean)top.s1.i;
	popTop();
ENDCODE
#endif

/**
 * ret (a9) 1: index<br>
 *	[No change]<br>
 *	continue execution from address taken from a local variable #index (the asymmetry with jsr is intentional)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_RET)
BEGCODE(RET)
	unimplemented();
ENDCODE
#endif

/**
 * return (b1) <br>
 *	==> [empty]<br>
 *	return void from method<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_RETURN)
BEGCODE(RETURN)
	pushTop();
	areturn();
	popTop();
ENDCODE
#endif

/**
 * saload (35) <br>
 *	arrayref, index ==> value<br>
 *	load short from array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_SALOAD)
BEGCODE(SALOAD)
	jint index=top.s1.i;
	jobject adr=apop();
	if (checkIndex(adr, index)) top.s1.i=(jshort)(((jshortArray)adr)->array[index]);
ENDCODE
#endif

/**
 * sastore (56) <br>
 *	arrayref, index, value ==><br>
 *	store short to array<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_SASTORE)
BEGCODE(SASTORE)
	jshort value=(jshort)top.s1.i;
	jint index=ipop();
	jobject adr=apop();
	popTop();
	if (checkIndex(adr, index)) 	((jshortArray)adr)->array[index]=value;
ENDCODE
#endif

/**
 * sipush (11) 2: byte1, byte2<br>
 *	==> value<br>
 *	pushes a short onto the stack<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_SIPUSH)
BEGCODE(SIPUSH)
	pushTop();
	top.s1.i=(jshort)GETCODEWORD2();
ENDCODE
#endif

/**
 * swap (5f) <br>
 *	value2, value1 ==> value1, value2<br>
 *	swaps two top.s1 words on the stack (note that value1 and value2 must not be double or long)<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_SWAP)
BEGCODE(SWAP)
	popTop0();
	pushTop();
	top.s1=top.s0;
ENDCODE
#endif

/**
 * tableswitch (aa) 4+: [0-3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3, highbyte4, jump offsets...<br>
 *	index ==><br>
 *	continue execution from an address in the table at offset index<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_TABLESWITCH)
BEGCODE(TABLESWITCH)
	jint index=top.s1.i;
	void * target;
	int32_t low, high;
	popTop();

	target=(void*)GETCODEADR2();
	low=GETCODEDWORD2();
	high=GETCODEDWORD2();
	if (low<=index && index<=high) {
		pc+=sizeof(void*)*(index-low);
		pc=(void*)GETCODEADR2();
	} else {
		pc=target;
	}
ENDCODE
#endif

/**
 * wide (c4) 3/5: opcode, indexbyte1, indexbyte2 or iinc, indexbyte1, indexbyte2, countbyte1, countbyte2<br>
 *	[same as for corresponding instructions]<br>
 *	execute opcode, where opcode is either iload, fload, aload, lload, dload, istore, fstore, astore, lstore, dstore, or ret, but assume the index is 16 bit; or execute iinc, where the index is 16 bits and the constant to increment by is a signed 16 bit short<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_WIDE)
BEGCODE(WIDE)
	unimplemented();
ENDCODE
#endif

/**
 * HAIKU: wiinc (c4) 6: indexbyte1, indexbyte2, countbyte1, countbyte2<br>
 *	[No change]<br>
 *	increment local variable #index by signed byte const<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_WIINC)
BEGCODE(WIINC)
	jstack top_s1 =(jstack)getLocal(GETCODEWORD2());
	top_s1->i+=(int16_t)GETCODEWORD2();
ENDCODE
#endif



/**
 * breakpoint (ca) <br>
 *	<br>
 *	reserved for breakpoints in Java debuggers; should not appear in any class file<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_BREAKPOINT)
BEGCODE(BREAKPOINT)
	unimplemented();
ENDCODE
#endif

/**
 * impdep1 (fe) <br>
 *	<br>
 *	reserved for implementation-dependent operations within debuggers; should not appear in any class file<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IMPDEP1)
BEGCODE(IMPDEP1)
	unimplemented();
ENDCODE
#endif

/**
 * impdep2 (ff) <br>
 *	<br>
 *	reserved for implementation-dependent operations within debuggers; should not appear in any class file<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_IMPDEP2)
BEGCODE(IMPDEP2)
	unimplemented();
ENDCODE
#endif

/**
 * xxxunusedxxx (ba) <br>
 *	<br>
 *	this opcode is reserved "for historical reasons"<br>
 */
#if ALL_BYTECODES || defined(HBC_DEF_XXXUNUSEDXXX)
BEGCODE(XXXUNUSEDXXX)
	unused();
ENDCODE
#endif
