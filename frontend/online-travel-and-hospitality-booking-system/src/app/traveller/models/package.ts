export interface Package {
    packageId: number;
    name: string;
    includedHotelIds: number[];
    includedFlightIds: number[];
    activities: string[];
    price: number;
    description: string;
    durationDays: number;
    destination: string;
    createdByAgentId: number;
    isActive: boolean;
}
