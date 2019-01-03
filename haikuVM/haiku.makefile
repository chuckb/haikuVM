################################################################################
# Is a copy of haiku.makefile from directory haikuVM. Edit with care.
################################################################################

-include ../../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
O_SRCS :=
C_SRCS :=
S_UPPER_SRCS :=
S_SRCS :=
OBJ_SRCS :=
ASM_SRCS :=
OBJS :=
C_DEPS :=
ASM_DEPS :=
EEPROM_IMAGE :=
FLASH_IMAGE :=
ELFS :=
LSS :=
S_DEPS :=
SIZEDUMMY :=
S_UPPER_DEPS :=

# Every subdirectory with source files must be described here
SUBDIRS :=



-include subdir.mk


USER_OBJS :=
LIBS := -lc 

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(S_DEPS)),)
-include $(S_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
endif

-include makefile.defs

# Add inputs and outputs from these tool invocations to the build variables
LSS += \
$(HAIKU_APP_NAME).lss \

FLASH_IMAGE += \
$(HAIKU_OUTPUT) \

EEPROM_IMAGE += \
$(HAIKU_APP_NAME).eep \

SIZEDUMMY += \
sizedummy \


# All Targets
ifeq ($(strip $(HAIKU_LINKER)),NONE)
all: none
else
ifeq ($(strip $(HAIKU_EXTENSION)),.hex)
all: $(HAIKU_APP_NAME).elf $(LSS) $(FLASH_IMAGE) $(EEPROM_IMAGE) $(SIZEDUMMY)
else
ifeq ($(strip $(HAIKU_EXTENSION)),.srec)
all: $(HAIKU_APP_NAME).srec sizedummy.rcx
else
all: $(HAIKU_APP_NAME)
endif
endif
endif

none:
	@echo
	
info:
	@echo '#'
	@echo '#'
	@echo '#'
	@echo '#############################################################'
	@echo '# cross compiling'
	@echo '#############################################################'
	@echo '#'
	@echo '#'
	@echo '#'
		
# Tool invocations
$(HAIKU_APP_NAME).elf: info $(OBJS) $(USER_OBJS) 
	@echo 'Building target: $@'
	@echo 'Invoking: C Linker'
	touch '$@'
	$(HAIKU_LINKER) $(HAIKU_LDFLAGS) 
	@echo 'Finished building target: $@'
	@echo ' '

$(HAIKU_APP_NAME).rcx: info $(OBJS) $(USER_OBJS) 
	@echo 'Building target: $@'
	@echo 'Invoking: C Linker'
	touch '$@'
	$(HAIKU_LINKER) $(HAIKU_LDFLAGS) 
	@echo 'Finished building target: $@'
	@echo ' '

$(HAIKU_APP_NAME).srec: $(HAIKU_APP_NAME).rcx 
	@echo 'Building target: $@'
	#h8300-hms-ld -Map $(HAIKU_APP_NAME).map -T$(NXJ_HOME)/hardware/tools/lejos_rcx/rcx.lds -u__start --oformat coff-h8300 -L. -L$(NXJ_HOME)/hardware/tools/lejos_rcx -o $(HAIKU_APP_NAME).rcx -l$(HAIKU_APP_NAME) -lrcx -lfloat -lc
	h8300-hms-ld -Map $(HAIKU_APP_NAME).map  -TC:/haikuVM/hardware/tools/lejos_rcx/rcx.lds  -u__start --oformat coff-h8300 -L. -LC:/haikuVM/hardware/tools/lejos_rcx  -o $(HAIKU_APP_NAME).rcx -l$(HAIKU_APP_NAME) -lrcx -lfloat -lc

	
	h8300-hms-objcopy -I coff-h8300 -O srec '$<'  '$@' 
	- h8300-hms-objdump --disassemble-all -S -C -s -t --no-show-raw-insn -m h8300 '$<' >dis.asm
	#$(JAVA) -cp $(NXJ_HOME)/haikufier/bin SrecDiff -f '$@' -i '$(NXJ_HOME)/image001.srec' -o diff.srec 
	#- cp '$(NXJ_HOME)/image001.srec' '$(NXJ_HOME)/image002.srec' 
	#cp '$@' '$(NXJ_HOME)/$@' 
	#cp '$@' '$(NXJ_HOME)/image001.srec' 
	@echo 'Finished building target: $@'
	@echo ' '


x$(HAIKU_OUTPUT): info $(OBJS) $(USER_OBJS) 
	@echo 'Building target: $(HAIKU_OUTPUT)'
	@echo 'Invoking: C Linker'
#	touch '$@'
	$(HAIKU_LINKER) $(HAIKU_LDFLAGS) 
	@echo 'Finished building target: $@'
	@echo ' '

$(HAIKU_APP_NAME): info $(OBJS) $(USER_OBJS) 
	@echo 'Building target: $(HAIKU_OUTPUT)'
	@echo 'Invoking: C Linker'
#	touch '$@'
	$(HAIKU_LINKER) $(HAIKU_LDFLAGS) 
	@echo 'Finished building target: $@'
	@echo ' '

$(HAIKU_APP_NAME).lss: $(HAIKU_APP_NAME).elf
	@echo 'Invoking: AVR Create Extended Listing'
	-avr-objdump -h -S '$<'  >'$@'
	@echo 'Finished building: $@'
	@echo ' '

$(HAIKU_APP_NAME).hex: $(HAIKU_APP_NAME).elf
	@echo 'Create Flash image (ihex format)'
	-avr-objcopy -R .eeprom -O ihex '$<' '$@'
	@echo 'Finished building: $@'
	@echo ' '

$(HAIKU_APP_NAME).eep: $(HAIKU_APP_NAME).elf
	@echo 'Create eeprom image (ihex format)'
	-avr-objcopy -j .eeprom --no-change-warnings --change-section-lma .eeprom=0 -O ihex '$<'  '$@'
	@echo 'Finished building: $@'
	@echo ' '

sizedummy: $(HAIKU_APP_NAME).elf
	@echo 'Invoking: Print Size'
	-avr-size --format=avr --mcu=$(HAIKU_TARGET) '$<'
	@echo 'Finished building: $@'
	@echo ' '

sizedummy.rcx: $(HAIKU_APP_NAME).srec
	@echo 'Invoking: Print Size'
	-h8300-hms-size --format=srec '$<'
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets

# Clean Target
ifeq ($(strip $(HAIKU_LINKER)),NONE)
clean: none
else
clean:
	-$(RM) $(OBJS)$(C_DEPS)$(ASM_DEPS)$(EEPROM_IMAGE)$(FLASH_IMAGE)$(ELFS)$(LSS)$(S_DEPS)$(SIZEDUMMY)$(S_UPPER_DEPS) $(HAIKU_APP_NAME).elf
	-@echo ' '
endif

upload:
	$(HAIKU_UPLOAD)

.PHONY: all clean dependents upload
.SECONDARY:

-include ../../makefile.targets
