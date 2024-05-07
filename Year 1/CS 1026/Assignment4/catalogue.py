# Computer Science 1026A - Section 001
# Ashna Mittal - 251206758

from country import Country

class CountryCatalogue:
    def __init__(self, countryFile):
        # Creating a List that contains the information of the country
        self._countryCat = []

        # Command to open the file in reading mode
        file = open(countryFile, 'r')

        #skipping the first line
        next(file)

        #looping through the file and adding it to the catalogue list
        for country in file:
            # Strip each line
            country = country.strip()
            # Remove '|' in each line
            country = country.split('|')
            # Create an object to country
            countries = Country(country[0], country[2], country[3], country[1])
            self._countryCat.append(countries)
            # command to close the file
        file.close()


    def setPopulationOfCountry(self, name, pop):
        # Use for loop to iterate through all countries
        for i in self._countryCat:
            if i.getName() == name:
                i.setPopulation(pop)

    def setAreaOfCountry(self, name, area):
        # Use for loop to iterate through all countries
        for i in self._countryCat:
            if i.getName() == name:
                 i.setArea(area)

    def setContinentofCountry(self, name, area):
        # Use for loop to iterate through all countries
        for i in self._countryCat:
            if i.getName() == name:
                 i.setContinent(area)

    def findCountry(self, country):
        # Use for loop to iterate through all countries
        for i in self._countryCat:
            # check condition to find if country object is in countryCat
            if i._name == country:
                return country

# Adding a country to a catelogue
    def addCountry(self, countryName, pop, area, cont):
        # Use for loop to iterate through all countries
        for i in self._countryCat:
            if i._name == countryName:
                return False

        self._countryCat.append(Country(countryName, pop, area, cont))

        return True

#printing the catalogue
    def printCountryCatalogue(self):
        for countries in self._countryCat:
            # prints a list of countries and their information
            print(countries)

#saving the catalogue
    def saveCountryCatalogue(self, filename):
        self._countryCat = sorted(self._countryCat)

        #opening the name of the file with the data and saving the data if added

        file = open(filename, 'w')
        # command to open the file in writing mode
        try:
            # loop to iterate for all objects
            for country in self._countryCat:
                file.write(country.name + '|' + country.continent + '|' + str(country.pop) + '|' + str(country.area) + '\n')

        #if the file fails to save it will return -1

        except:
             return -1


    def countryCat(self):
        return self._countryCat
