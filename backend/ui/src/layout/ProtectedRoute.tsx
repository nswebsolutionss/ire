import { useMemo, useState} from "react"
import { Outlet} from "react-router-dom"
import { LoadingSpinner } from "../components/atoms/LoadingSpinner";

export default function ProtectedRoute() {
  const [isSignedIn, setIsSignedIn] = useState(false)

  useMemo(() => {
    fetch('http://localhost:8084/api/authenticated', {
      method: 'GET', 
      credentials: 'include',
      mode: 'cors'                    
      })
  .then(response => {
      setIsSignedIn(true)
  }).catch(err => {
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

  }, [])

  return (
    <>
    {isSignedIn ? <Outlet/> : <LoadingSpinner/>}
    
    </>
  )

}