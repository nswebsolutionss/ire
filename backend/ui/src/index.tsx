import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './index.css';
import RootLayout from './layout/RootLayout';
import reportWebVitals from './reportWebVitals';
import { DashboardPage } from './routes/DashboardPage';
import LandingPage from './routes/LandingPage';
import ForSalePage from './routes/ForSalePage';
import PropertySaleResults from './routes/PropertySaleResults';
import { APIProvider } from "@vis.gl/react-google-maps";



import { Provider } from 'react-redux';
import { store } from './redux/store';
import ProtectedRoute from './layout/ProtectedRoute';

const router = createBrowserRouter([
  {
    element: <RootLayout />,
    children: [
      { path: "/", element: <LandingPage /> },
      { path: "/for-sale", element: <ForSalePage /> },
      { path: "/property-for-sale", element: <PropertySaleResults /> },



      {
        element: <ProtectedRoute />,
        path: "dashboard",
        children: [
          { path: "/dashboard", element: <DashboardPage /> },
        ]
      }
    ]
  }
]
)

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <Provider store={store}>
    <APIProvider apiKey={"AIzaSyB4pyPkA-1zcA4bZK1ymVF59__EshNWibo"}>
      <RouterProvider router={router} />
      </APIProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
