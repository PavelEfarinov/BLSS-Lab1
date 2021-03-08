import psycopg2


def establish_database_connection():
    print('connecting to the database...')
    connection = psycopg2.connect(host='localhost',
                                  database='postgres',
                                  user='postgres',
                                  password='password')
    connection.autocommit = True
    return connection
