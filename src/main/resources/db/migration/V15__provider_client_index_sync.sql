SELECT setval(
               pg_get_serial_sequence('provider', 'id'),
               COALESCE((SELECT MAX(id) FROM provider), 1)
       );

SELECT setval(
               pg_get_serial_sequence('client', 'id'),
               COALESCE((SELECT MAX(id) FROM client), 1)
       );