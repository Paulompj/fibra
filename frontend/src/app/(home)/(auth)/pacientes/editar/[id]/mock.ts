import { ICustomerAndAppointments } from "@/types/customers";
import { ICustomerType } from "@/types/customerTypes";

export const mockUsers: ICustomerAndAppointments[] = [
  {
    id: "1",
    fullName: "John Doe",
    phone: "(+55) 98765-4321",
    age: 35,
    address: "Rua das Flores, 123, São Paulo, SP",
    photoUrl: "",
    customerType: "adulto",
    appointmentsId: ["1", "7", "11"],
    appointmentsCount: 3,
  },
  {
    id: "2",
    fullName: "Jane Smith",
    phone: "(+55) 91234-5678",
    age: 28,
    address: "Avenida Paulista, 1000, São Paulo, SP",
    photoUrl: "",
    customerType: "mirim",
    appointmentsId: ["2"],
    appointmentsCount: 1,
  },
  {
    id: "3",
    fullName: "Robert Johnson",
    phone: "(+55) 99876-5432",
    age: 42,
    address: "Rua Augusta, 500, São Paulo, SP",
    photoUrl: "",
    customerType: "familiar",
    appointmentsId: ["3"],
    appointmentsCount: 1,
  },
  {
    id: "4",
    fullName: "Emily Davis",
    phone: "(+55) 97654-3210",
    age: 31,
    address: "Rua Oscar Freire, 300, São Paulo, SP",
    photoUrl: "",
    customerType: "adulto",
    appointmentsId: ["4"],
    appointmentsCount: 1,
  },
];

export const mockCustomerTypes: ICustomerType[] = [
  {
    id: "1",
    name: "adulto",
  },
  {
    id: "2",
    name: "mirim",
  },
  {
    id: "3",
    name: "familiar",
  },
];
