import React, { useRef } from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './index.css';
import RootLayout from './layout/RootLayout';
import reportWebVitals from './reportWebVitals';
import {LandingPage, PropertySaleResults, DashboardPage} from '@dwellio/pages';
import { APIProvider } from "@vis.gl/react-google-maps";
import { PersistGate } from 'redux-persist/integration/react';


import { Provider } from 'react-redux';
import { store, persistor } from './redux/store';
import ProtectedRoute from './layout/ProtectedRoute';

const router = createBrowserRouter([
  {
    element: <RootLayout />,
    children: [
      { path: "/", element: <LandingPage/> },
      { path: "/property-for-sale", element: <PropertySaleResults/> },



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
      {/* <PersistGate loading={null} persistor={persistor}> */}
        <APIProvider apiKey={"AIzaSyB4pyPkA-1zcA4bZK1ymVF59__EshNWibo"}>
          <RouterProvider router={router} />
        </APIProvider>
      {/* </PersistGate> */}
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
