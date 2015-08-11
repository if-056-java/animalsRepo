angular.module('AnimalsListService', [])
    .service('AnimalsListService', [function() {
        var animals = [
            {id: '1', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''},
            {id: '4', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''},
            {id: '2', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''},
            {id: '5', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''},
            {id: '3', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''},
            {id: '6', sex: 'Самець', type: 'Собака', size: 'm', cites: '', sort: 'Німецька вівчарка', transpNumber: '451251231', tokenNumber: '156123', dateOfRegister: '1998-05-16', dateOfBirth: '1998-01-17', dateOfSterilization: '1999-01-16', color: 'Стандарт', owner: 'Вася Пупкін', address: 'м. Львів, Чорновола 20/3', image: '', serviceID: ''}
        ]; 
        
        this.getAnimals = function() {
            return animals;    
        }
        
        this.getAnimal = function(animalId) {
            for(var i = 0, len = animals.length; i < len; i++) {
                if (parseInt(animals[i].id) === parseInt(animalId)) {
                    return animals[i];
                }
            }
            return {};    
        }
        
        this.removeAnimal = function(animalId) {
            for(var i = 0; i < animals.length; i++) {
                if (parseInt(animals[i].id) === parseInt(animalId)) {
                    //delete data from db
                    animals.splice(i, 1); //must be changed
                    console.log('animal with id: ' + animalId + ' deleted.');
                    //get data from rest
                    //
                }
            } 
            return animals;
        }
        
        this.setAnimal = function(animal) {
            for(var i = 0; i < animals.length; i++) {
                if (parseInt(animals[i].id) === parseInt(animal.Id)) {
                    /*animals[i].sex = animals.sex;
                    animals[i].type = animals.type;
                    animals[i].size = animals.size;
                    animals[i].cites = animals.cites;
                    animals[i].sort = animals.sort;
                    animals[i].transpNumber = animals.transpNumber;
                    animals[i].tokenNumber = animals.tokenNumber;
                    animals[i].dateOfRegister = animals.dateOfRegister;
                    animals[i].dateOfBirth = animals.dateOfBirth;
                    animals[i].dateOfSterilization = animals.dateOfSterilization;
                    animals[i].color = animals.color;
                    animals[i].owner = animals.owner;
                    animals[i].address = animals.address;
                    animals[i].image = animals.image;
                    animals[i].serviceID = animals.serviceID;*/
                    animals[i] = animal;
                }
            }
        }
    }]);