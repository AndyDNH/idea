INSERT INTO books (
    isbn, version, title, price
) VALUES
      ('EASDR', 145, 'Cronica de una muerte anunciada', 15.99),
      ('BK001', 1, 'Cien años de soledad', 22.50),
      ('BK002', 2, 'Don Quijote de la Mancha', 18.75),
      ('BK003', 1, 'El amor en los tiempos del cólera', 19.99),
      ('BK004', 3, 'La sombra del viento', 25.40),
      ('BK005', 1, 'Rayuela', 17.30),
      ('BK006', 4, 'Pedro Páramo', 14.20),
      ('BK007', 2, 'La ciudad y los perros', 21.10),
      ('BK008', 1, 'Ficciones', 16.80),
      ('BK009', 5, 'El túnel', 13.95),
      ('BK010', 2, 'Ensayo sobre la ceguera', 20.00),
      ('BK011', 1, 'Aura', 12.50),
      ('BK012', 3, 'Los detectives salvajes', 24.99),
      ('BK013', 2, 'Como agua para chocolate', 15.60),
      ('BK014', 1, 'La casa de los espíritus', 23.45)
;


INSERT into books_authors (book_isbn, authors_id) VALUES
      ('BK001', 2),
      ('BK002', 3)
;
