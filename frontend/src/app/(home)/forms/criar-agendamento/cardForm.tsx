"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Form } from "@/components/ui/form";
import AddressInputs from "./addressInputs";
import PersonalInputs from "./personalInputs";
import SelectInputs from "./selectInputs";
import { CalendarInput } from "./calendarInput";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { IServiceList } from "@/types/services";

const formSchema = z.object({
  fullname: z.string().min(10).max(100),
  phone: z.string().min(11).max(20),
  age: z.number().int().nonnegative().min(1).max(120),
  address: z.string().min(20).max(200),
  customerType: z.string(),
  service: z.string(),
  professionalId: z.string(),
  dateTime: z.date().min(new Date()),
});

export default function CardForm() {
  const [selectedService, setSelectedService] = useState<
    IServiceList | undefined
  >(undefined);
  const [selectedProfessional, setSelectedProfessional] = useState<string>("");

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
  });

  async function handleSubmit({ ...values }: z.infer<typeof formSchema>) {
    const formData = values;
    console.log(formData);

    try {
      new Promise((resolver) => setTimeout(resolver, 500));
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(handleSubmit)}
        className="flex flex-col md:grid md:grid-cols-2 md:grid-rows-[1fr_auto_auto] align-middle gap-6 gap-y-8 w-full h-full"
      >
        <PersonalInputs form={form} />
        <AddressInputs form={form} />
        <SelectInputs
          form={form}
          selectedService={selectedService}
          setSelectedService={setSelectedService}
          setSelectedProfessional={setSelectedProfessional}
        />
        <CalendarInput
          form={form}
          selectedService={selectedService}
          selectedProfessional={selectedProfessional}
        />
        <Button
          className="col-start-1 col-end-3 w-full max-w-[500px] hover:cursor-pointer justify-self-center"
          type="submit"
        >
          Enviar
        </Button>
      </form>
    </Form>
  );
}
