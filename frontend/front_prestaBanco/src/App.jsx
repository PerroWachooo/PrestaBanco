import React, { useState, useEffect } from 'react';
import './App.css'
import Home from './components/Home'
import Navbar from './components/Navbar';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import AddEditUser from './components/AddEditUsesr';
import UsersList from './components/UsersList';
import SimulateLoan from './components/SimulateLoan';
import AddSolicitud from './components/AddSolicitud';
import LoanAplicationList from './components/LoanAplicationList';
import TotalCostLoan from './components/TotalCostLoan';



function App() {
  return (
    <BrowserRouter>
    
      <div className="container">
      <Navbar></Navbar> 
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/user/list" element={<UsersList />} />
          <Route path= "/user/register" element={<AddEditUser/>}/>
          <Route path= "/user/register/:id" element={<AddEditUser/>}/>
          <Route path= "/simulation" element={<SimulateLoan/>}/>
          <Route path= "/aplication" element={<AddSolicitud/>}/>
          <Route path= "/aplication/:id" element={<AddSolicitud/>}/>
          <Route path= "/aplication/list" element={<LoanAplicationList/>}/>
          <Route path= "/aplication/totalCost" element={<TotalCostLoan/>}/>"
        </Routes>

      </div>
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