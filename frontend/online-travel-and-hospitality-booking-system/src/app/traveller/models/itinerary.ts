export interface Itinerary {
  packageId: number;
  customizationDetails: string;
  startDate: string; // ISO string format
  endDate: string;   // ISO string format
  numberOfTravelers: number;
}