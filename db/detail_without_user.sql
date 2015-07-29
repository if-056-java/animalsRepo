SELECT 
a.ID as ID, a.image as Image, a.dateofRegister as RegisterDate, a.dateofsterilization as SterilizationDate, a.color,
b.type as Type,
c.size as Size,
d.sex as Sex,
e.type as Sites

FROM animals.animals a  
left join animals.animaltypes b on b.id=a.typeid
left join animals.animalsizes c on c.id=a.sizeid
left join animals.animalsextypes d on d.id = a.sextypeid
left join animals.animalcitestypes e on e.id = a.citesid;