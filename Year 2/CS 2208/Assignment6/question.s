 AREA delete, code, READONLY
     ENTRY

eos    EQU 0x00               ;Defining a null constant for comparison use cases
t      EQU 0x74               ;Defining 't' for comparison use cases
spce   EQU 0x20               ;Defining ' ' (space) for comparison use cases. Here0x20 is the hexadecimal value of the ASCII value of space which is 32. 
byte   EQU 8                  ;Defining #number of bits in 1 byte
       ADR  r1, STRING1       ;Let r1 be a pointer pointing to String1
       ADR  r2, STRING2       ;Let r2 be a pointer pointing to String2
       LDR  r3, =0x74686520   ;Storing "the" for comparison use cases
;Scenario 1 : Start of the String sentence
       LDRB r5, [r1]          ;Loading the very first character From String 1 into r5
       CMP  r5, #t            ;Comparison case which is checking if the character extracted is 't'
       BEQ  check             ;If the above check condition is true, branch to check if "he " or "he\0" follows 
;LOOP
loop   LDRB r5, [r1], #1      ;Loading a character from String1 into r5, and then incrementing the pointer after
       STRB r5, [r2], #1      ;Storing the character in r5, and then incrementing pointer after
;Scenario 2 : End of String sentence
       CMP  r5, #eos          ;Checking if the extracted character is the eos character or not
       BEQ  inf               ;If the above check condition is true, jump to the end of the program and skip the lines in between
       CMP  r5, #spce         ;If the above check condition is false, check if the extracted character is a space. If yes, "the " or "the\0" may follow
       BNE  loop              ;loop UNTIL the extracted character is a space
;Either the user is at the start of the sentence or the character extracted is a space
check  MOV r6, r1             ;Saving the current position of the pointer for String 1 in r6 just in case the user needs to go back
       MOV  r4, #0            ;Resetting r4, as it will be pointting at a SubString of the next 4 characters in String1
build  LDRB r5, [r1], #1      ;Building the SubString by adding one character at a time and loading in r5
       CMP  r5, #eos          ;Checking if the extracted character is eos character or not
       LDREQ r3, =0x74686500  ;If eos and the SubString equals "the\0", it will be removed from the string
       ADD r4, r5             ;Appending the character to the SubString in r4
       CMP  r4, #0x10000000   ;Checking if the SubString contains less than 4 characters
       LSLLT r4, #byte        ;If the above check condition is true then shift the SubString 1 byte left
       BLT  build             ;Continue building the SubString
       CMP  r4, r3            ;Checking if SubString is "the " or "the\0" 
       SUBEQ r1, #1           ;If the above check condition is equal, decrement the pointer to String 1 in order to preserve space or null character 
       MOVNE r1, r6           ;Else If the above check condition is Not equal, the SubString should not be removed and so return the pointer to String 1 back to the initial position
    B loop                    ;Repeat processing until string 1's eos character is reached
inf B inf                     ;End program with an infinite loop to prevent error

 AREA delete, DATA, READWRITE
STRING1 DCB  "and the man said they must go" ;String1
EoS     DCB   0x00                           ;end of string1
STRING2 space 0x7F                           ;String2 with "the" removed; just allocating 127 bytes
        ALIGN

     END