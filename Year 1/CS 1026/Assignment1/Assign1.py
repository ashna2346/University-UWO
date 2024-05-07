# The code below computes the cost of electricity using The times of Use method and calculates the various discounts alonside
import sys
# importing the sys module to use the exit() command
while True:
    # while loop executes till condition is false and the loop is terminated
    OffPeakunit = float(input("Enter kwh during Off Peak period: "))
    # The value of the KWH units used in the Off peak period is entered by the user
    OffPeakcost = 0.085 * OffPeakunit
    # rate of the KWH units used in the off peak period is calculated
    if OffPeakcost != 0.0:
        OnPeakunit = float(input("Enter kwh during On Peak period: "))
        # The value of the KWH units used in the On peak period is entered by the user
        OnPeakcost = 0.176*OnPeakunit
        # rate of the KWH units used in the On peak period is calculated
        MidPeakunit = float(input("Enter kwh during Mid Peak period: "))
        # The value of the KWH units used in the Mid peak period is entered by the user
        MidPeakcost = 0.119 * MidPeakunit
        # rate of the KWH units used in the off peak period is calculated
        seniornot = input("Is owner senior? (Y,y,N,n): ")
        # The user enters if the owner is a senior citizen or not
        TotalUnit = OffPeakunit + OnPeakunit + MidPeakunit
        # Total KWH units used in the all three periods is calculated
        TotalUnitcost = OffPeakcost + OnPeakcost + MidPeakcost
        # The total cost of the Total KWH used is calculated
        if TotalUnit < 400:
            TotalUnitcost = TotalUnitcost - (0.04*TotalUnitcost)
            # if the total units used is less than 400 then a discount of 4% is deducted
        elif OnPeakunit < 150:
            OnPeakcost = OnPeakcost - (0.05 * OnPeakcost)
            TotalUnitcost = OffPeakcost + OnPeakcost + MidPeakcost
            # if the units used in the On peak period is less than 150 then a discount of 5% is deducted from On peak and the new total unit cost is calculated for further discounts
        if seniornot == 'Y' or seniornot == 'y':
            TotalUnitcost = TotalUnitcost-(0.11*TotalUnitcost)
            # if the owner is a senior citizen then a discount of 11% is deducted from the total cost
        TotalUnitcost = TotalUnitcost + (0.13*TotalUnitcost)
        # after applying all the discounts, a tax of 13% is charged on the total cost
        print("Electricity Cost: $" + format(TotalUnitcost, ".2f"))
        # The final calculated electricity cost is printed upto two decimal places
        print()
    else:
        sys.exit()
        # The program terminates since the loop condition is false and the else part commands an immediate exit
