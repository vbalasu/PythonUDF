# https://stackoverflow.com/questions/16460468/can-we-call-a-python-method-from-java
class Hello:  
    __gui = None  

    def __init__(self, gui):  
        self.__gui = gui  

    def run(self):  
        print 'Hello world!'
