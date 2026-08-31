SELECT setval(
               pg_get_serial_sequence('users', 'id'),
               COALESCE((SELECT MAX(id) FROM users), 1)
       );