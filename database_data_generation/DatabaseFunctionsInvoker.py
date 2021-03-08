from random_entities_generator import generate_base_user, generate_store_item


class DatabaseFunctionsInvoker:
    def __init__(self, connection):
        self.connection = connection
        self.cursor = connection.cursor()

    def create_base_user(self):
        user = generate_base_user()
        self.cursor.execute('insert into base_user(email, login, name, surname, password) values(%s, %s, %s, %s, %s)',
                            (user['email'], user['login'], user['name'],
                             user['surname'], 'pass'))

    def create_store_item(self):
        item = generate_store_item()
        self.cursor.execute('insert into store_item(currently_available, description, name) values(%s, %s, %s)',
                            (item['stored'], item['description'],
                             item['name']))
