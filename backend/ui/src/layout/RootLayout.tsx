import { ClerkProvider } from '@clerk/clerk-react';
import { Outlet, useNavigate } from 'react-router-dom';
 
const PUBLISHABLE_KEY = import.meta.env.VITE_CLERK_PUBLISHABLE_KEY
 
if (!PUBLISHABLE_KEY) {
  throw new Error("Missing Publishable Key")
}
 
export default function RootLayout() {
  const navigate = useNavigate();
  
  return (
    <ClerkProvider navigate={navigate} publishableKey={PUBLISHABLE_KEY}>
      <main>
        <Outlet />
      </main>
    </ClerkProvider>
  )
}