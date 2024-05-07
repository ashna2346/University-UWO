# Computer Science 1026A - Section 001
# Ashna Mittal - 251206758

class Country:


    def __init__(self, name, pop, area, continent):
        # constructor
        self.name = name
        self.pop = pop
        self.area = area
        self.continent = continent
        #instance variables

    # Getter Methods
    def getName(self):
        return self.name

    def getPopulation(self):
        return self.pop

    def getArea(self):
        return self.area

    def getContinent(self):
        return self.continent

    # Setter methods
    def setPopulation(self, pop):
        self.pop = pop

    def setArea(self, area):
        self.area = area

    def setContinent(self, continent):
        self.continent = continent

    # Sorting the country names in alphabetical order
    def __lt__(self, other):
        return self.name[0] < other.name[0]

    #  Generates a string representation for class objects
    def __repr__(self):
        return "{} (pop: {}, size: {}) in {}".format(self.name, self.pop, self.area, self.continent)

    def name(self):
        return self.name
