import { useUser } from "@clerk/clerk-react"
import { useMemo } from "react"
import { Outlet, useNavigate } from "react-router-dom"
import { LoadingSpinner } from "../components/atoms/LoadingSpinner"

export default function ProtectedRoute() {
  const { isSignedIn, isLoaded } = useUser()
  const navigate = useNavigate()
  useMemo(() => {
    if (isLoaded && !isSignedIn) {
      navigate("/")
    }

  }, [isSignedIn, isLoaded])

  return (
    <>
      {!isLoaded ? <LoadingSpinner /> : <Outlet />}
    </>
  )

}