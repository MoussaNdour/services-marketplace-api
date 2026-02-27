--removing all clients tables linked to users with provider role--
delete from client where userId in (select id from users where role='PROVIDER');


--removing all providers tables linked to users with client role--
delete from provider where userId in (select id from users where role='CLIENT');

