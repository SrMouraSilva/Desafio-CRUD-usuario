set -e

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

EOF

#db.users.insert({ "address": { "city": "Paris", "zip": "123" }, "name": "Mike", "phone": "1234" });