UPDATE user
set id=?,
    role=?,
    name=?,
    surname=?,
    login=?,
    password=?,
    blacklist = ?
where id = ?;