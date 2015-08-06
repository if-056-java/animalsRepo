SELECT a.ID as ID, b.type as type, c.size as size, a.image FROM animals.animals a  
left join animals.animaltypes b on b.id=a.typeid
left join animals.animalsizes c on c.id=a.sizeid;