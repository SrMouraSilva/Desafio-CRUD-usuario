# pip install faker
from faker import Faker
import json

fake = Faker()
Faker.seed(4321)


def make_person():
    return {
        "fullName": fake.name(),
        "email": fake.ascii_company_email(),
        # S3cretP@ssword
        "password": "$argon2id$v=19$m=4096,t=3,p=1$b7l8GxEsfDNFGQxW37TG4w$x0VcbejuPZhTAL2MekdvBbIqVcUJYoLKtJMrBQyvNdo",
        "phone": fake.phone_number(),
        "address": {
            "country": fake.country(),
            "state": fake.postcode(),
            "street": {
                "zipCode": fake.postcode(),
                "name": fake.street_address(),
                "number": fake.building_number(),
                "complement": None
            }
        },
        "profile": "USER"
    }


for i in range(25):
    print("new_db.users.insert("+json.dumps(make_person())+");")

