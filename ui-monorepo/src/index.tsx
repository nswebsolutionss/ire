import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './index.css';
import RootLayout from './layout/RootLayout';
import reportWebVitals from './reportWebVitals';
import { DashboardPage } from './routes/DashboardPage';
import LandingPage from './routes/LandingPage';

import { Provider } from 'react-redux';
import { store } from './redux/store';
import ProtectedRoute from './layout/ProtectedRoute';

const router = createBrowserRouter([
  {
    element: <RootLayout />,
    children: [
      { path: "/", element: <LandingPage /> },
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
      <RouterProvider router={router} />
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
