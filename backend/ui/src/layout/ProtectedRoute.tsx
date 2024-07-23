import { useUser } from "@clerk/clerk-react"
import { useMemo, useState } from "react"
import { Outlet, redirect, useNavigate } from "react-router-dom"
import { LoadingSpinner } from "../components/atoms/LoadingSpinner"

export default function ProtectedRoute() {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
  const { isSignedIn, isLoaded } = useUser()
  const navigate = useNavigate()
  useMemo(() => {
    fetch('http://localhost:8084/api/authenticated', {
      method: 'GET', 
      credentials: 'include',
      mode: 'cors'                    
      })
  .then(response => {
      if(response.ok) {
        setIsLoggedIn(true)
      }
      else {
        setIsLoggedIn(false)
      }
  }).catch(err => {
    setIsLoggedIn(false)
    fetch('http://localhost:8084/login', {
      method: 'GET', 
       mode: 'cors'                    
      })
  .then(response => {
      const redirectUrl = response.headers.get("Location");
      if(redirectUrl) {
          window.location.replace(redirectUrl)

      }
  })
  })

  }, [isLoggedIn])

  return (
    <>
      {!isLoggedIn ? <LoadingSpinner /> : <Outlet />}
    </>
  )

}