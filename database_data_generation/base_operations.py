tables = ['base_user', 'shopping_cart', 'store_item']

def reset_database(connection):
    cur = connection.cursor()
    for table in tables:
        cur.execute(f'delete from {table}')
    cur.close()
