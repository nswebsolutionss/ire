import { Route, Routes } from 'react-router';
import LandingPage from './routes/LandingPage';
import Dashboard from './dashboard/dashboard/Dashboard';
import { Provider } from 'react-redux';
import { store } from './redux/store';


function Main() {
  return (
    <Provider store={store}>
      <Routes>
        <Route path='/' element={<LandingPage />} />
        <Route path='/dashboard' element={<Dashboard />}/>
      </Routes>
    </Provider>

  );
}

export default Main;