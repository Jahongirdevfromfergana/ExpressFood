package uz.fergana.fastexpress.screen.restaurant.detail.feedback

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.fergana.fastexpress.models.request.MakeRatingRequest
import uz.fergana.fastexpress.screen.restaurant.detail.ItemClickListener
import uz.fergana.fastexpress.databinding.FragmentFeedbackBinding

private const val ARG_PARAM1 = "id"

class FeedbackFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private var param1: String? = null
    lateinit var binding: FragmentFeedbackBinding
    lateinit var viewModel: FeedbackViewModel
    var request = MakeRatingRequest("", 0, 1)
    private var mListener: ItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        binding.apply {

            viewModel = ViewModelProvider(requireActivity())[FeedbackViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.successOrderLiveData.observe(requireActivity()) {

            }

            back.setOnClickListener {
                dismiss()
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReview.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener = if (context is ItemClickListener) {
            context
        } else {
            throw RuntimeException(
                context.toString() + "Must implement ItemClickListener"
            )
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FeedbackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onClick(p0: View?) {
        val id = arguments?.getString("id")?.toInt()

        request.comment = binding.tvComment.text.toString()
        request.restaurantId = id!!
        request.rating = binding.tvRating.rating.toInt()
        viewModel.getMakeRating(request)
        Toast.makeText(requireActivity(), "Update Rating", Toast.LENGTH_SHORT).show()

        viewModel.getAllRestaurant()

        viewModel.successRestaurantsLiveData .observe(requireActivity()) {
            it.forEach {
                if (id == it.id) {
                    mListener!!.onItemClick(it)
                    dismiss()
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}