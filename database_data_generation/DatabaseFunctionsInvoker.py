from random_entities_generator import generate_base_user, generate_store_item, generate_order


class DatabaseFunctionsInvoker:
    def __init__(self, connection):
        self.connection = connection
        self.cursor = connection.cursor()

    def create_base_user(self):
        user = generate_base_user()
        self.cursor.execute('insert into base_user(email, login, name, surname, password) values(%s, %s, %s, %s, %s)',
                            (user['email'], user['login'], user['name'],
                             user['surname'], 'pass'))
        return user

    def create_store_item(self):
        item = generate_store_item()
        self.cursor.execute('insert into store_item(currently_available, description, name) values(%s, %s, %s)',
                            (item['stored'], item['description'],
                             item['name']))
        return item

    def create_order(self, user):
        order = generate_order()
        self.cursor.execute('select id from base_user where login = %s', (user['login'],))
        user_id = self.cursor.fetchone()
        self.cursor.execute(
            'insert into orders(address, formed_date, order_status, payment_status, client_id) values(%s, %s, %s, %s, %s)',
            (
                order['address'], order['formed_date'], order['order_status'], order['payment_status'], user_id,))

        self.cursor.execute('select id from orders where address = %s', (order['address'],))
        order_id = self.cursor.fetchone()

        self.cursor.execute('select id from store_item')
        item_ids = self.cursor.fetchmany(3)
        for item in item_ids:
            self.cursor.execute('insert into order_items(quantity, orders_id, store_item_id) values(%s, %s, %s)',
                                (4, order_id, item))
