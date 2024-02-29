import { Route, Routes } from 'react-router';
import LandingPage from './routes/LandingPage';
import Dashboard from './routes/Dashboard';
import { Provider } from 'react-redux';
import { store } from './dashboard/property/propertySlice';


function Main() {
  return (
    <Routes>
        <Route path='/' element={<LandingPage/>}/>
        <Route path='/dashboard' element={<Provider store={store}><Dashboard/></Provider>}/>
    </Routes>

  );
}

export default Main;