set -e

# mongosh: for mongo version 6
mongosh <<EOF
use $MONGO_INITDB_DATABASE

db.createUser({
  user: '$MONGO_INITDB_USER',
  pwd: '$MONGO_INITDB_PWD',
  roles: [{
    role: 'readWrite',
    db: '$MONGO_INITDB_DATABASE'
  }]
})

conn = new Mongo();

new_db = conn.getDB("$MONGO_INITDB_DATABASE");
new_db.users.createIndex({ "email": 1 }, { unique: true });

new_db.users.insert({
    "fullName": "Admin",
    "email": "admin@example.com",
    // S3cretP@ssword
    "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo",
    "phone": null,
    "address": {
        "country": "Brazil",
        "state": "Ceará",
        "street": {
            "zipCode": "00000-000",
            "name": "Rua Administativa",
            "number": null,
            "complement": null
        }
    },
    "profile": "ADMIN"
});

EOF
