import random
import string

from faker import Faker

from organization_names import top_companies_2000
from popular_names import top_200_names, top_1000_surnames


def generate_base_user():
    user_name = random.choice(top_200_names)
    user_surname = random.choice(top_1000_surnames)
    user_email = get_email(user_name, user_surname)
    user_login = get_login(user_name, user_surname)
    return {'name': user_name,
            'surname': user_surname,
            'email': user_email,
            'login': user_login}


def get_email(name: string, surname: string):
    return '{0}{1}@{2}'.format(name.lower(), surname.lower(),
                               Faker().free_email_domain())


def get_login(name: string, surname: string):
    return '{0}_{1}_{2}'.format(name.lower(), surname.lower(),
                                ''.join(random.choices(string.digits, k=4)))


def generate_store_item():
    name = random.choice(top_companies_2000) + ' ' + Faker().word()
    description = Faker().paragraph(nb_sentences=2)
    stored = random.randint(100, 110)
    return {'name': name,
            'description': description,
            'stored': stored}


def generate_order():
    address = Faker().address()
    date = Faker().iso8601()
    return {'address': address,
            'formed_date': date,
            'order_status': 'formed',
            'payment_status': 'online'}
