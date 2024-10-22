import React, { useState, useEffect } from 'react';
import './App.css'
import Home from './components/Home'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';



function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App

/*  <>
   <div>
     <a href="https://vitejs.dev" target="_blank">
       <img src={viteLogo} className="logo" alt="Vite logo" />
     </a>
     <a href="https://react.dev" target="_blank">
       <img src={reactLogo} className="logo react" alt="React logo" />
     </a>
   </div>
   <h1>Vite + React</h1>
   <div className="card">
     <button onClick={() => setCount((count) => count + 1)}>
       count is {count}
     </button>
     <p>
       Edit <code>src/App.jsx</code> and save to test HMR
     </p>
   </div>
   <p className="read-the-docs">
     Click on the Vite and React logos to learn more
   </p>
 </> */