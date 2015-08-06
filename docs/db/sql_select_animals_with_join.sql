SELECT  a.ID, a.transpNumber, a.tokenNumber, a.dateOfRegister, a.dateOfBirth, a.dateOfSterilization, a.color, a.sort, a.image, a.isActive, 
 b.service, 
 c.sex, 
 d.size, 
 e.type, 
 f.type,
 
 g.postIndex,
 g.city,
 g.street,
 g.houseNumber,
 g.apartmentNumber,
 
 h.surname,
 h.name,
 h.email,
 h.phone,
 h.organizationName,
 h.organizarionInfo,
 i.postIndex as userpostindex,
 i.city as usercity,
 i.street as userstreet,
 i.houseNumber as userhousenumber,
 i.apartmentNumber as userapartmentnumber
 
 
 FROM animals.animals a 
 left join animals.animalservices b on b.ID = a.serviceId
 left join animals.animalsextypes c on c.ID = a.sexTypeId
 left join animals.animalsizes d on d.ID = a.sizeId
 left join animals.animaltypes e on e.ID = a.typeId
 left join animals.animalcitestypes f on f.ID = a.citesId
 left join animals.addresses g on g.ID = a.addressId
 left join animals.users h on h.ID = a.userId
 left join animals.addresses i on i.ID = h.addressId
 
 where a.ID = 2;