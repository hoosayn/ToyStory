IF NOT EXISTS (SELECT 1 FROM sys.databases WHERE name = 'The_Toy_Story_Donor')
  BEGIN
    CREATE DATABASE The_Toy_Story_Donor;
  END;