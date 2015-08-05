SELECT animals.id, 
animalsextypes.id AS asex_id, animalsextypes.sex AS asex, 
animaltypes.id AS atype_id , animaltypes.type AS atype, 
animalsizes.id AS asize_id, animalsizes.size AS asize,
animalscitestypes.id AS cites_id, animalscitestypes.type AS cites_type, 
animals.sort, animals.transpNumber, animals.tokenNumber, animals.dateOfRegister, 
animals.dateOfBirth, animals.dateOfSterilization, animals.color, 
users.id AS user_id, users.name AS user_name, users.surname AS user_surname,
addresses.id AS addresses_id, addresses.country AS addresses_country, 
addresses.region AS addresses_region, addresses.town AS addresses_town, 
addresses.street AS addresses_street, addresses.postIndex AS addresses_post_index, 
animals.isActive, animals.image,
animalservices.id AS animalservices_id, animalservices.service AS animalservices_service 
FROM animals
INNER JOIN animalsextypes ON animals.sexTypeId = animalsextypes.id 
INNER JOIN animaltypes ON animals.typeId = animaltypes.id 
INNER JOIN animalsizes ON animals.sizeId = animalsizes.id
INNER JOIN animalscitestypes ON animals.citesId = animalscitestypes.id
INNER JOIN users ON animals.userId = users.id
INNER JOIN addresses ON animals.addressId = addresses.id
INNER JOIN animalservices ON animals.serviceId = animalservices.id