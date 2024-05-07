 AREA upc, CODE, READONLY
		ENTRY
		
		ADR	     r0, UPC				;Set r0 to point to UPC	
toInt	EQU	     48					;Subtract 48 from ASCII value to get the corresponding integer value
	
									;LOOP to run for digits at odd positions in UPC
AddOdd	LDRB 	r2, [r0, r1]			;Load the byte of UPC at position pointed to by r1
		SUB 	     r2, #toInt			;Convert the byte's ASCII value to integer value
		ADD 	     r3, r2				;Add integer value to total
		ADD		r1, #2				;Increment counter by 2 to point to next odd digit
		CMP		r1, #12				;Check position of r1 pointer if past UPC, exit or continue loop
		BNE 	AddOdd					;IF branch that runs UNTIL loop is complete and odd digits are totaled	
		MOV		r1, #1				;ELSE branch that Resets pointer to 1 to sum even digits
									
									;LOOP to run for digits at even positions in UPC
AddEven	LDRB 	r2, [r0, r1]			;Load the byte of UPC at position pointed to by r1
		SUB		r2, #toInt			;Convert byte's ASCII value into integer
		ADD 	     r4, r2				;Add integer value to total 
		ADD		r1, #2				;Increment counter by 2 to point to next even digit
		CMP		r1, #11				;Check position of r1 pointer if pointing at check digit, exit or continue loop
		BNE 	AddEven					;IF branch thta runs UNTIL loop is complete and even digits except check digit are totaled
		
		ADD		r3, r3, r3, LSL #1		;Multiply sum of odd digits by 2 via left shift, then add it to r3
									;Equivalent to multiplying original sum by 3
		ADD		r4, r3				;Add even and odd sums calculated above, and store in r4
		SUB 	     r4, #1			 	;Subtract 1 from total sum stored in r4
		
									;LOOP
RptSub	CMP 	     r4, #9				;Check condition if total is less than or equal to 9
		SUBGT 	r4, #10				;If total is greater than 9, subtract 10 from total
		BHI 	RptSub					;UNTIL remaining total is less than or equal to 9
									
		RSB 	     r5, r4, #9			;Subtract the remainder from 9, store in r5 which is the calculated check digit
		LDRB 	r6, [r0, r1]			;Load UPC check digit into r6
		SUB 	     r6, #toInt			;Subtract 48 from check digit's ASCII value to obtain its corresponding integer value 
		MOV 	     r0, #2				;Set r0 to 2 - if check digits match r0 will change to 1 else it stays 2
		CMP 	     r5, r6				;Compare UPC check digit with calculated check digit
		MOVEQ 	r0, #1				;1 is stored in r0 if check digits match and UPC is valid

Loop	B		Loop					     ;Infinite loop to prevent error and Exit
		
UPC 	DCB 	"013800150738"		 	          ;UPC String
		END