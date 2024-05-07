# Computer Science 1026A - Section 001
# Ashna Mittal - ID 251206758
# Assign2 prompts the user for input and and then calls the functions of code_check
from code_check import basic_code, positional_code, universal_pc
# importing modules from code_check
codes = []
basic = []
positional = []
upc = []
none = []
# list initialization
while True:
    nn = input("Please enter code (digits only) (enter 0 to quit) ")
    # value entered by the user
    if int(nn) > 0:
        if basic_code(nn) is True:
            basic.append(nn)
            print("--code: %s valid Basic code." % nn )
            # checking the value in the basic_code function

        if positional_code(nn) is True:
            positional.append(nn)
            print("--code: %s valid Position code." % nn)
            # checking the value in the positional code function

        if universal_pc(nn) is True:
            upc.append(nn)
            print("--code: %s valid UPC code." % nn)
            # checking the value in the universal product code function

        if (basic_code(nn) is False) and (positional_code(nn) is False) and (universal_pc(nn) is False):
            none.append(nn)
            print("--code: %s not Basic, Position or UPC code." % nn)
            # this condition executes if all of the three functions return false
    else:
        break
        # while loop terminates if the value entered is 0
print("\nSummary")
if basic != []:
    print("Basic : "+','.join(basic))
else:
    print("Basic : None")

if positional != []:
    print("Position : "+','.join(positional))
else:
    print("Position : None")

if upc != []:
    print("UPC : "+','.join(upc))
else:
    print("UPC : None")

if none != []:
    print("None : "+','.join(none))
else:
    print("None: None")
# printing of the summary of the code
