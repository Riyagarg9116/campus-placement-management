from datasets import load_dataset

print("Loading placement dataset...")

dataset = load_dataset("Krooz/Campus_Recruitment_CSV")

# Save all three splits separately
for split in ["train", "validation", "test"]:

    data = dataset[split].to_pandas()

    filename = f"placement_{split}.csv"

    data.to_csv(filename, index=False)

    print(f"\n{split.upper()} dataset saved!")
    print("Shape:", data.shape)

print("\nAll datasets saved successfully!")
