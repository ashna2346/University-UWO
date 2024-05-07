# Computer Science 1026A - Section 001
# Ashna Mittal - 251206758

from catalogue import CountryCatalogue


def processUpdates(cntryFileName, updateFileName, badUpdateFile):
    done = False

    while not done:
        try:
            file = open(updateFileName, 'r')
            # making a list with the updates in the update file
            update_list = []
            bad_file = open(badUpdateFile, 'w')
            # looping through the update file
            for line in file:
                # strips and splits the update file
                line = line.strip('\n').split(';')
                # adding the newly stripped and split lines to the update list
                update_list.append(line)
            # looping through the update list

            for country in update_list:
                 # looping through only after the first index in each update
                for update in country[1:]:
                    # checking if the update is too long
                    if len(country) > 4:
                        # writing to the invalid file
                        bad_file.write(str(country) + '\n')
                    # if there is no country in the update
                    if len(country[0]) < 0:
                        # writing to the invalid file
                        bad_file.write(str(country) + '\n')
                    # if the first letter of the country is not uppercase
                    if country[0][0] != country[0][0].upper():
                        # writing to the invalid file
                        bad_file.write(str(country) + '\n')
                    # if the updates do not have an equal sign between signifier and value
                    if update[1] != '=':
                        # writing to invalid file
                        bad_file.write(str(country) + '\n')
                     # if the country is not valid
                    if not country[0].replace('_', '').isalpha():
                        # writing to the invalid file
                        bad_file.write(str(country) + '\n')

            # opening the file with the countries
            datafile = open(cntryFileName)
            # making a catalogue object of the file with countries
            catlog = CountryCatalogue(cntryFileName)
            # looping through the data file

            for countries in datafile:
                # making three empty strings of new population, new continent, and new area
                newPop = ""
                newContinent = ""
                newArea = ""
                # looping through each country in the new update list
                for country in update_list:
                    # looping through each update in the country
                    for update in country:
                    # if statement to identify if there is a population update
                        if update[0] == 'P' and update[1] == '=':
                            # replacing the P= in the update with empty characters to give us the value
                            newPop = update.replace('P', '').replace('=', '')
                            # if statement to identify if there is an area update
                        if update[0] == 'A' and update[1] == '=':
                            # replacing the A= in the update with empty characters to give us the value
                            newArea = update.replace('A', '').replace('=', '')
                        # if statement to identify if there is a continent update
                        if update[0] == 'C' and update[1] == '=':
                            # replacing the C= in the update with empty characters to give us the value
                            newContinent = update.replace('C', '').replace('=', '')

                        # lopping through the country catalogue
                        for existingCountry in catlog._countryCat:
                            # if statement to identify if the country is in the catalogue
                            if country[0] in existingCountry.name:

                                # setting the new population to the country
                                catlog.setPopulationOfCountry(country[0], newPop)

                                # setting the new continent to the country
                                catlog.setContinentofCountry(country[0], newContinent)

                                # setting the new area to the country
                                catlog.setAreaOfCountry(country[0], newArea)

            #saving new catalogue to output File
            catlog.saveCountryCatalogue('output.txt')
            return True, catlog
        #if file not found it will prompt a question and if it is answered
        except FileNotFoundError:
            x = input("File not found. Do you wish to quit? 'Y'(yes) or 'N'(no): ")
            if x.upper() == 'Y':
                output = open('output.txt', 'w')
                output.write('Updated Unsuccessful\n')
                done = True
                return False, None
            else:
                done = False
                cntryFileName = input('Enter name of file with country data: ')
                updateFileName = input('Enter name of file with country updates: ')
