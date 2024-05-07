# Computer Science 1026A - Section 001
# Ashna Mittal - ID 251206758
# code_check consists of three functions
def basic_code(n):
    # function to determine if the number entered is a valid base identification number
    s = 0
    for i in n[:-1]:
        # loop runs till the second last number
        i = int(i)
        s += i
        # sum of digits
    d = s % 10
    if d == int(n[-1]):
        return True
    else:
        return False
    # the function returns True if the base identification number is valid


def positional_code(n):
    # function to determine if the number entered is a valid positional code
    s = 0
    p = 1
    for i in n[:-1]:
        # loop runs till the second last number
        i = int(i)
        s += i*p
        # multiplication of each digit by its position in the base identification number and calculation of sum
        p += 1
    dd = s % 10
    if dd == int(n[-1]):
        return True
    else:
        return False
        # the function returns True if the positional code is valid


def universal_pc(n):
    # function to determine if the number entered is a valid universal product code
    s = 0
    ps = 1
    for i in n[:-1]:
        # loop runs till the second last number
        i = int(i)
        if ps % 2 != 0:
            # check condition for odd number
            s += 3*i
            # if digit is at odd place, adding it after multiplying by 3
        else:
            s += i
            # if digit is at even place then directly adding it
        ps += 1
    ddd = s % 10
    if ddd != 0:
        ddd = 10 - ddd
    if ddd == int(n[-1]):
        return True
    else:
        return False
    # the function returns True if the universal product code is valid
