import { useAuth, useUser } from "@clerk/clerk-react"
import { useEffect, useMemo } from "react"
import { Outlet, useNavigate } from "react-router-dom"
import { LoadingSpinner } from "../components/atoms/LoadingSpinner"
import React from "react"

export default function ProtectedRoute() {
  const { isSignedIn, isLoaded } = useUser()
  const navigate = useNavigate()
  console.log('test', isSignedIn)
  useMemo(() => {
    if (isLoaded && !isSignedIn) {
      navigate("/")
    }
  }, [isSignedIn, isLoaded])

  return (
    <>
      {!isLoaded ? <LoadingSpinner/> : <Outlet />}
    </>
  )

}