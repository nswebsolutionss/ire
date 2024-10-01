


export interface SizeInterface {
    xs: string
    sm: string
    md: string
    lg: string
    xl: string
    xxl: string
  }
  
export const Size = {
    xs: '400px', // for small screen mobile
    sm: '600px', // for mobile screen
    md: '900px', // for tablets
    lg: '1280px', // for laptops
    xl: '1440px', // for desktop / monitors
    xxl: '1920px', // for big screens
}
  
  export const device = {
    xs: `(max-width: ${Size.xs})`,
    sm: `(max-width: ${Size.sm})`,
    md: `(max-width: ${Size.md})`,
    lg: `(max-width: ${Size.lg})`,
    xl: `(max-width: ${Size.xl})`,
    xxl: `(max-width: ${Size.xxl})`,
  }