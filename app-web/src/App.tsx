import {useState} from 'react'

import './App.css'
import axios from "axios";

interface Author {
    id: number;
    name: string;
}

interface Book {
    isbn: string;
    price: number,
    title: string;
    authors: Array<Author>;
}

function App() {
    const [authors, setAuthors] = useState<Author[]>([]);
    const [books, setBooks] = useState<Book[]>([])

    const handleClickListarAutores = () => {
        axios.get<Author[]>("/app-authors/authors")
            .then(response => {
                setAuthors(response.data);
            })
            .catch(error => alert(error));
    };

    const handleClickListarLibros = () => {
        axios.get<Book[]>("/app-books/books")
            .then(response => {
                setBooks(response.data);
            })
            .catch(error => alert(error));
    };

    return (    
        <>
            <section id="center">
                <div>
                    <h1>Autores</h1>
                    <p>
                        Edit <code>src/App.tsx</code> and save to test <code>HMR</code>
                    </p>

                    <br/>

                    <button onClick={handleClickListarAutores}>Consultar</button>

                    <br/>
                    {
                        authors.map(author => (
                            <p key={author.id}>{author.id} - {author.name}</p>
                        ))
                    }


                    <br/>
                    <button onClick={handleClickListarLibros}>Consultar Libros</button>

                    <br/>
                    {
                        books.map(book => (
                            <ul key={book.isbn}>
                                <li>{book.isbn} - {book.title}</li>
                                {

                                    book.authors.map(author =>
                                        `${author.name}`)
                                }
                            </ul>
                        ))
                    }
                </div>
            </section>

        </>
    )
}

export default App
