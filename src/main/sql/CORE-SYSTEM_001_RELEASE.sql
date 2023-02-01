-- CORE_001_RELEASE.sql from 08/11/2022 12:30 PM

-- 14/12/2022 12:00 PM SHEENA AP
-- Create database core with owner postgres
CREATE DATABASE core  WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;

-- 14/12/2022 12:00 PM SHEENA AP
-- Create schema system
CREATE SCHEMA system;

-- 14/12/2022 12:00 PM SHEENA AP
-- Create schema hr
CREATE SCHEMA hr;

-- 14/12/2022 12:15 PM SHEENA AP
-- Create table user
CREATE TABLE system.user_account (
user_id SERIAL NOT NULL PRIMARY KEY,
user_name TEXT NOT NULL,
password TEXT NOT NULL,
password_token TEXT,
verification_token TEXT,
company_id INTEGER NOT NULL,
status INTEGER NOT NULL,
created_by INTEGER NOT NULL,
updated_by INTEGER NOT NULL,
created_at timestamp without time zone NOT NULL,
update_at timestamp without time zone NOT NULL);

-- 14/12/2022 12:20 PM SHEENA AP
-- Create table status codes
CREATE TABLE public.status_code (
status_code INTEGER NOT NULL PRIMARY KEY,
status_name TEXT NOT NULL,
status_code_context TEXT NOT NULL,
notes TEXT);

-- 14/12/2022 12:30 PM SHEENA AP
-- Inserting status codes for active and inactive user accounts
INSERT INTO public.status_code (status_code,status_name,status_code_context,notes) values
(100,'Active User','User account',' '),
(101,'Inactive User','User account',' ');

-- 14/12/2022 12:00 PM SHEENA AP
-- Create schema org
CREATE schema org;

-- 14/12/2022 12:00 PM SHEENA AP
-- Create table company
CREATE TABLE org.company(
company_id SERIAL NOT NULL PRIMARY KEY,
company_code TEXT NOT NULL UNIQUE,
company_name TEXT NOT NULL,
status INTEGER NOT NULL REFERENCES public.status_code(status_code),
created_by INTEGER REFERENCES system.user_account(user_id),
updated_by INTEGER REFERENCES system.user_account(user_id),
created_at timestamp without time zone NOT NULL,
update_at timestamp without time zone NOT NULL);

-- 14/12/2022 12:50 PM SHEENA AP
-- Alter table user_account to add foreign key constrain to status
ALTER TABLE system.user_account ADD FOREIGN KEY(status) REFERENCES public.status_code(status_code);

-- 14/12/2022 12:50 PM SHEENA AP
-- Alter table user_account to add foreign key constrain to company_id
ALTER TABLE system.user_account ADD FOREIGN KEY(company_id) REFERENCES org.company(company_id);

-- 28/12/2022 03:15 PM SHEENA AP
-- Alter table user_account to add column email
ALTER TABLE system.user_account ADD COLUMN email TEXT NOT NULL;

-- 28/12/2022 03:15 PM SHEENA AP
-- Insert values to status codes for company context
INSERT INTO public.status_code (status_code,status_name,status_code_context,notes) values
(110,'Active Company','Company',' '),
(111,'Inactive Company','Company',' ');

-- 28/12/2022 03:15 PM SHEENA AP
-- Insert values to company table
INSERT INTO org.company(company_code,company_name,status,created_at,update_at) VALUES('INV','Innovature Software Labs',110,'2020-11-01 17:03:06','2020-11-01 17:03:06');

-- 28/12/2022 03:15 PM SHEENA AP
-- Insert values to user
INSERT INTO system.user_account (user_name,password,email,status,company_id,created_by,updated_by,created_at,update_at) VALUES('Sheena','123456','sheena.ap@innovaturelabs.com',100,1,1,1,'2020-11-01 17:03:06','2020-11-01 17:03:06');

-- 28/12/2022 04:00 PM SHEENA AP
-- Update company to set created_by and updated_by
UPDATE org.company SET created_by=1,updated_by=1;

-- 28/12/2022 04:00 PM SHEENA AP
-- Alter company to set not null constraint to created_by and updated_by
ALTER TABLE  org.company ALTER COLUMN created_by SET NOT NULL;
ALTER TABLE  org.company ALTER COLUMN updated_by SET NOT NULL;

-- 28/12/2022 04:00 PM SHEENA AP
-- Create table staff
CREATE TABLE hr.staff(
staff_id SERIAL NOT NULL PRIMARY KEY,
staff_name TEXT NOT NULL,
employee_code TEXT NOT NULL,
status INTEGER NOT NULL REFERENCES public.status_code(status_code),
company_id INTEGER NOT NULL REFERENCES org.company(company_id),
created_at timestamp without time zone NOT NULL,
update_at timestamp without time zone NOT NULL);

-- 28/12/2022 04:00 PM SHEENA AP
-- Alter table staff to add columns created_by,updated_by,email,address,date_of_birth,department,phone_number
ALTER TABLE hr.staff ADD COLUMN created_by INTEGER REFERENCES hr.staff(staff_id);
ALTER TABLE hr.staff ADD COLUMN updated_by INTEGER REFERENCES hr.staff(staff_id);
ALTER TABLE hr.staff ADD COLUMN email TEXT NOT NULL UNIQUE;
ALTER TABLE hr.staff ADD COLUMN address TEXT;
ALTER TABLE hr.staff ADD COLUMN date_of_birth TEXT;
ALTER TABLE hr.staff ADD COLUMN department INTEGER NOT NULL;
ALTER TABLE hr.staff ADD COLUMN phone_number TEXT NOT NULL;

-- 28/12/2022 04:00 PM SHEENA AP
-- Alter table staff to rename columns
ALTER TABLE hr.staff RENAME COLUMN created_at TO date_created;
ALTER TABLE hr.staff RENAME COLUMN update_at TO date_modified;
ALTER TABLE hr.staff RENAME COLUMN created_by TO staff_created;
ALTER TABLE hr.staff RENAME COLUMN updated_by TO staff_modified;

-- 28/12/2022 04:00 PM SHEENA AP
-- Alter table company to rename columns
ALTER TABLE org.company RENAME COLUMN created_at TO date_created;
ALTER TABLE org.company RENAME COLUMN update_at TO date_modified;
ALTER TABLE org.company RENAME COLUMN created_by TO staff_created;
ALTER TABLE org.company RENAME COLUMN updated_by TO staff_modified;

-- 28/12/2022 04:00 PM SHEENA AP
-- Alter table usr account to rename columns
ALTER TABLE system.user_account RENAME COLUMN created_at TO date_created;
ALTER TABLE system.user_account RENAME COLUMN update_at TO date_modified;
ALTER TABLE system.user_account RENAME COLUMN created_by TO staff_created;
ALTER TABLE system.user_account RENAME COLUMN updated_by TO staff_modified;

-- 30/12/2022 03:15 PM SHEENA AP
-- Insert values to status codes for staff
INSERT INTO public.status_code (status_code,status_name,status_code_context,notes) values
(120,'Active Staff','Staff',' '),
(121,'Inactive Staff','Staff',' ');

-- 30/12/2022 03:15 PM SHEENA AP
-- Insert values to staff table
INSERT INTO hr.staff (staff_id,staff_name,employee_code,status,company_id,date_created,date_modified,staff_created,staff_modified,email,address,date_of_birth,department,phone_number)
VALUES(1,'System Admin','INV-0001',120,1,'2020-11-01 17:03:06','2020-11-01 17:03:06',1,1,'admin@gmail.com','Test Address','01/01/2011',1,'7845129636');

-- 30/12/2022 03:15 PM SHEENA AP
-- Alter table user account to add column
ALTER TABLE system.user_account add COLUMN staff_id INTEGER NOT NULL REFERENCES hr.staff(staff_id) DEFAULT 1;

-- 05/01/2023 03:20 PM SHEENA AP
-- Alter table user account to remove column user_name
ALTER TABLE system.user_account DROP COLUMN user_name;

-- 06/01/2023 04:00 PM SHEENA AP
-- Alter table staff to set not null constraint
ALTER TABLE hr.staff ALTER COLUMN date_created SET NOT NULL;
ALTER TABLE hr.staff ALTER COLUMN date_modified SET NOT NULL;
ALTER TABLE hr.staff ALTER COLUMN staff_created SET NOT NULL;
ALTER TABLE hr.staff ALTER COLUMN staff_modified SET NOT NULL;

-- 06/01/2023 04:00 PM SHEENA AP
-- Alter table company to set not null constraint
ALTER TABLE org.company ALTER COLUMN date_created SET NOT NULL;
ALTER TABLE org.company ALTER COLUMN date_modified SET NOT NULL;
ALTER TABLE org.company ALTER COLUMN staff_created SET NOT NULL;
ALTER TABLE org.company ALTER COLUMN staff_modified SET NOT NULL;

-- 06/01/2023 04:00 PM SHEENA AP
-- Alter table usr account to set not null constraint
ALTER TABLE system.user_account ALTER COLUMN date_created SET NOT NULL;
ALTER TABLE system.user_account ALTER COLUMN date_modified SET NOT NULL;
ALTER TABLE system.user_account ALTER COLUMN staff_created SET NOT NULL;
ALTER TABLE system.user_account ALTER COLUMN staff_modified SET NOT NULL;

-- 06/01/2023 04:00 PM SHEENA AP
-- Alter table usr account to add column google_refresh_token
ALTER TABLE system.user_account ADD COLUMN google_refresh_token TEXT;

-- 01/02/2023 10:00 AM SHEENA AP
-- Alter table staff to add composite key (email, company_id)
ALTER TABLE hr.staff ADD UNIQUE (email, company_id);

-- 01/02/2023 10:00 AM SHEENA AP
-- Alter table staff to drop constraint staff_email_key,company_created_by_fkey,company_updated_by_fkey
ALTER TABLE hr.staff DROP CONSTRAINT staff_email_key;
ALTER TABLE org.company DROP CONSTRAINT company_created_by_fkey;
ALTER TABLE org.company DROP CONSTRAINT company_updated_by_fkey;

-- 01/02/2023 11:00 AM SHEENA AP
-- Alter table staff to foreign keys to staff_created,staff_modified
ALTER TABLE org.company ADD FOREIGN KEY (staff_created) REFERENCES hr.staff(staff_id);
ALTER TABLE org.company ADD FOREIGN KEY (staff_modified) REFERENCES hr.staff(staff_id);

-- 01/02/2023 11:00 AM SHEENA AP
-- Insert new company to org.company table
INSERT INTO org.company(company_id,company_code,company_name,status,staff_created,staff_modified,date_created,date_modified) VALUES(2,'PMCR','Pinmiro',110,30,30,'2020-11-01 17:03:06','2020-11-01 17:03:06');