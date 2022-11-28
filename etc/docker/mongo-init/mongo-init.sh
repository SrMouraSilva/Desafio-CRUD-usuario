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


new_db.users.insert({"fullName": "Jason Brown", "email": "daniellewilson@williams.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "(936)672-7018x27349", "address": {"country": "Nigeria", "state": "28133", "street": {"zipCode": "74082", "name": "3973 Young Turnpike Suite 661", "number": "54933", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Michael Oconnor", "email": "rfleming@davies-park.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "781-140-0341x8906", "address": {"country": "Mayotte", "state": "55917", "street": {"zipCode": "45233", "name": "38411 Fisher Vista", "number": "812", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Monique Brown", "email": "payers@taylor.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "035-583-9046x40103", "address": {"country": "Denmark", "state": "27557", "street": {"zipCode": "43270", "name": "765 Hailey Curve", "number": "913", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Brian Miller", "email": "jamie41@hamilton.net", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "001-712-938-1281x518", "address": {"country": "Bosnia and Herzegovina", "state": "45947", "street": {"zipCode": "11710", "name": "38565 Keith Common Suite 620", "number": "956", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Sean Weeks", "email": "dscott@johnson.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-520-662-6583x7427", "address": {"country": "Croatia", "state": "23014", "street": {"zipCode": "69533", "name": "1143 Jennifer Wells Apt. 987", "number": "08626", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Matthew Fry", "email": "joshua01@meza.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "941-299-7094x4121", "address": {"country": "Venezuela", "state": "01497", "street": {"zipCode": "59703", "name": "49707 Ian Dale", "number": "8931", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Shirley Nguyen", "email": "juliejohnston@bennett.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "636.588.5286x60633", "address": {"country": "Congo", "state": "09676", "street": {"zipCode": "61349", "name": "595 Joshua Port", "number": "1904", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Jim Thompson", "email": "ovaughn@parker.info", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "4127115300", "address": {"country": "Ethiopia", "state": "88347", "street": {"zipCode": "63224", "name": "93856 Kyle Drive", "number": "27713", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Jennifer Berry", "email": "clarkdavid@mejia-george.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "001-641-229-9492", "address": {"country": "Angola", "state": "35052", "street": {"zipCode": "12460", "name": "66728 Williams Stravenue Suite 636", "number": "97743", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Joshua Ford", "email": "anthonymyers@howard-anderson.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "834.453.2547x87462", "address": {"country": "Vanuatu", "state": "25739", "street": {"zipCode": "32131", "name": "450 Richard Place", "number": "01016", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Matthew Jones", "email": "ramirezemma@phillips-odonnell.org", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "(422)866-9580", "address": {"country": "Peru", "state": "25669", "street": {"zipCode": "04610", "name": "849 Oneal Summit", "number": "41098", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Jimmy Marsh", "email": "hayleybryant@young.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "383-693-8273", "address": {"country": "Nigeria", "state": "34601", "street": {"zipCode": "52713", "name": "98825 Derek River Apt. 351", "number": "0051", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Thomas Young", "email": "ayoung@lambert.biz", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "333.864.3680x581", "address": {"country": "Djibouti", "state": "91577", "street": {"zipCode": "62401", "name": "0150 Jacob Creek", "number": "613", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Robert Walker", "email": "james45@parker-brown.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "(853)103-2233", "address": {"country": "Eritrea", "state": "67605", "street": {"zipCode": "14638", "name": "3392 Garcia Camp Apt. 919", "number": "6702", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Karen Wagner", "email": "obradley@montgomery-mullen.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-425-165-3432x640", "address": {"country": "Holy See (Vatican City State)", "state": "14576", "street": {"zipCode": "38792", "name": "13451 Jennifer Glen", "number": "39508", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Carrie Washington", "email": "benjaminfigueroa@ruiz.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-045-033-6597x32341", "address": {"country": "Saint Barthelemy", "state": "37987", "street": {"zipCode": "91081", "name": "72113 Chelsea Lodge Suite 589", "number": "2774", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Tara Hawkins", "email": "kavila@morse-moreno.biz", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "236-854-4758", "address": {"country": "Palestinian Territory", "state": "46153", "street": {"zipCode": "23831", "name": "651 Gibson Key", "number": "6065", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Linda Martinez", "email": "nrubio@cruz.org", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-568-861-5785", "address": {"country": "Cote d'Ivoire", "state": "46818", "street": {"zipCode": "12217", "name": "7627 Lisa Neck", "number": "8809", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Deborah Cowan", "email": "rodney11@turner.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "001-368-625-5155x97421", "address": {"country": "Macao", "state": "66660", "street": {"zipCode": "20849", "name": "879 Andrew Lane Apt. 335", "number": "0571", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Chelsea Huerta", "email": "bobbynelson@smith.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-892-667-8225x20479", "address": {"country": "Vanuatu", "state": "52403", "street": {"zipCode": "33801", "name": "90867 Steven Ramp", "number": "538", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Kimberly Ford", "email": "bperry@campbell.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "153.012.1379x49242", "address": {"country": "Saint Pierre and Miquelon", "state": "85007", "street": {"zipCode": "15933", "name": "75393 Tracy Well Apt. 460", "number": "60897", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Darren Walters", "email": "williamsnancy@morgan.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "(549)978-6111", "address": {"country": "Tokelau", "state": "06416", "street": {"zipCode": "68651", "name": "65422 Anderson Lights Suite 257", "number": "0803", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Ronald Stephenson", "email": "qcrosby@jones-collins.com", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "+1-502-113-7400x3638", "address": {"country": "Colombia", "state": "52085", "street": {"zipCode": "24806", "name": "288 Melissa Pines Apt. 555", "number": "1267", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Richard Clayton", "email": "katherine26@wise.org", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "477.416.1573x2422", "address": {"country": "Austria", "state": "25096", "street": {"zipCode": "95276", "name": "480 Sanchez Drive", "number": "763", "complement": null}}, "profile": "USER"});
new_db.users.insert({"fullName": "Melissa Craig", "email": "michellehorton@newman-dominguez.org", "password": "\$argon2id\$v=19\$m=4096,t=3,p=1\$b7l8GxEsfDNFGQxW37TG4w\$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo", "phone": "822.274.9301", "address": {"country": "Costa Rica", "state": "61624", "street": {"zipCode": "36955", "name": "03598 Powers Junction", "number": "59886", "complement": null}}, "profile": "USER"});

EOF
