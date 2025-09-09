export interface Booking {
  bookingId?: number;   // optional since it's auto-generated
  userId: number;
  type: BookingType;
  status: string;
  paymentId?: number;   // optional if not assigned yet
}

export enum BookingType {
  HOTEL = 'HOTEL',
  FLIGHT = 'FLIGHT'
}
export interface Flight {
  flightId?: number;    // optional since it's auto-generated
  airline: string;
  departure: string;
  arrival: string;
  price: number;
  availability: boolean;
}
export interface Hotel {
  hotelId?: number;     // optional since it's auto-generated
  name: string;
  location: string;
  roomsAvailable: number;
  rating: number;
  pricePerNight: number;
}
