from DatabaseFunctionsInvoker import DatabaseFunctionsInvoker
from base_operations import reset_database
from database_connector import establish_database_connection

connection = establish_database_connection()
cur = connection.cursor()

try:
    reset_database(connection)

    print('generating more data.')
    dfi = DatabaseFunctionsInvoker(connection)

    for i in range(200):
        dfi.create_base_user()
        dfi.create_store_item()

finally:
    cur.close()
    connection.close()
