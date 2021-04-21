tables = ['delivery_car_flight', 'courier', 'order_item', 'item_in_cart', 'orders', 'base_user', 'store_item', ]

def reset_database(connection):
    cur = connection.cursor()
    for table in tables:
        cur.execute(f'delete from {table}')
    cur.close()
