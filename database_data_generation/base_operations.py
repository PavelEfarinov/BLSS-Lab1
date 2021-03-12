tables = ['base_user', 'store_item', 'courier', 'delivery_car_flight']

def reset_database(connection):
    cur = connection.cursor()
    for table in tables:
        cur.execute(f'delete from {table}')
    cur.close()
